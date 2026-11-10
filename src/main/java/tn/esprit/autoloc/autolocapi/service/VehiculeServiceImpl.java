package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Utils;
import tn.esprit.autoloc.autolocapi.calcul.ITarificationStrategy;
import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    private final Map<VehiculeOffre, ITarificationStrategy> strategies;

    public VehiculeServiceImpl(IVehiculeRepository repository, List<ITarificationStrategy> strategyList) {
        super(repository);
        // Injection de toutes les stratégies via Spring IoC
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ITarificationStrategy::getOffre, s -> s));
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
}