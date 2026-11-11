package tn.esprit.autoloc.autolocapi.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Utils;
import tn.esprit.autoloc.autolocapi.calcul.ITarificationStrategy;
import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    private final Map<VehiculeOffre, ITarificationStrategy> strategies;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeServiceImpl(IVehiculeRepository repository, List<ITarificationStrategy> strategyList , IAgenceRepository agenceRepository,IEquipementRepository equipementRepository) {
        super(repository);
        // Injection de toutes les stratégies via Spring IoC
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ITarificationStrategy::getOffre, s -> s));
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
    }

    @Override
    protected String getEntityName() { return "Vehicule"; }

    @Override
    protected Long getId(Vehicule v) { return v.getIdVehicule(); }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule v, boolean isNew) {
        return v != null
                && Utils.isNotEmpty(v.getImmatriculation())
                && Utils.isNotEmpty(v.getMarque())
                && Utils.isNotEmpty(v.getModele())
                && v.getCategorie() != null
                && v.getStatut() != null
                && v.getTarifJournalier() != null;
    }

    @Override
    public double calculTarif(Long idVehicule, int jours, VehiculeOffre offre) {
        Vehicule v = findById(idVehicule);
        ITarificationStrategy strategy = strategies.get(offre);
        if (strategy == null) {
            throw new IllegalArgumentException("Offre inconnue : " + offre);
        }
        return strategy.calculer(v.getTarifJournalier().doubleValue(), jours);
    }
    @Transactional
    @Override
    public Vehicule desaffecterAgence(Long id) {
        var vehicule = findById(id);
        vehicule.setAgence(null);
        return repository.save(vehicule);
    }

    @Override
    public Vehicule affecterAgence(Long id, Long agenceId) {
        var vehicule = findById(id);
        var agence =agenceRepository.findById(agenceId).orElseThrow(() -> new ResourceNotFoundException("Agence",agenceId.toString()));
        vehicule.setAgence(agence);
        return repository.save(vehicule);
    }
    @Override
    @Transactional // Assure que la transaction est bien gérée
    public Vehicule affecterEquipement(Long idVehicule, Long idEquipement) {
        // 1. Récupérer le véhicule
        Vehicule vehicule = findById(idVehicule);

        // 2. Récupérer l'équipement
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement.toString()));

        // 3. Ajouter l'équipement au véhicule
        vehicule.getEquipements().add(equipement);
        // Optionnel mais recommandé pour la cohérence bidirectionnelle :
        equipement.getVehicules().add(vehicule);

        // 4. Sauvegarder
        return repository.save(vehicule);
    }

}