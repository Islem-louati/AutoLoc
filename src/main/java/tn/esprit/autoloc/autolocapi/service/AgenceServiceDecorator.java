// package tn.esprit.autoloc.autolocapi.service;
package tn.esprit.autoloc.autolocapi.service;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.io.IRapport;
import tn.esprit.autoloc.autolocapi.io.RapportFactory;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

import java.util.List;

@Primary
@Service
public class AgenceServiceDecorator extends AgenceServiceImpl {
    private final IEmployeService employeService;

    private final IVehiculeService vehiculeService;


    public AgenceServiceDecorator(IAgenceRepository repository, IVehiculeService vehiculeService,IEmployeService employeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
    }

    @Override
    public String simuler(Long idAgence, int jours, VehiculeOffre offre) {
        Agence agence = findById(idAgence);
        IRapport rapport = RapportFactory.createRapport();

        String entete = "Agence ID: " + agence.getIdAgence() +
                " | Nom: " + agence.getNom() +
                " | Tel: " + agence.getTelephone();
        rapport.afficherEntete(entete);

        double totalGeneral = 0;
        for (Vehicule v : agence.getVehicules()) {
            double tarif = vehiculeService.calculTarif(v.getIdVehicule(), jours, offre);
            totalGeneral += tarif;
            rapport.afficherLigne("Vehicule " + v.getImmatriculation() + " : " + tarif + " DT");
        }

        // Mise à jour du total dans le rapport (si on utilise l'interface)
        if (rapport instanceof tn.esprit.autoloc.autolocapi.io.RapportConsole) {
            ((tn.esprit.autoloc.autolocapi.io.RapportConsole) rapport).setTotal(totalGeneral);
        }

        return "Rapport généré. Total : " + totalGeneral + " DT";
    }
     @Override
    @Transactional // TRÈS IMPORTANT pour la Question 17
    public Agence addEmployesToAgence(Long idAgence, List<Employe> employes) {
        // 1. Mettre à jour d'abord l'agence
        Agence agence = findById(idAgence);

        // 2. Utiliser le service des employés pour insérer les employés
        for (Employe employe : employes) {
            employe.setAgence(agence);
            employeService.create(employe); // Insertion en base
            agence.addEmploye(employe);     // Utilisation de la méthode de la Q10
        }

        // 3. Mettre à jour le champ effectif
        agence.setEffectif(agence.getEmployes().size());

        // 4. Sauvegarder l'agence
        return repository.save(agence);
    }
}