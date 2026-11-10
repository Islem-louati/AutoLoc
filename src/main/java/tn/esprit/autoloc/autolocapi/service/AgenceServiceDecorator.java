// package tn.esprit.autoloc.autolocapi.service;
package tn.esprit.autoloc.autolocapi.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.io.IRapport;
import tn.esprit.autoloc.autolocapi.io.RapportFactory;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;

@Primary
@Service
public class AgenceServiceDecorator extends AgenceServiceImpl {

    private final IVehiculeService vehiculeService;

    public AgenceServiceDecorator(IAgenceRepository repository, IVehiculeService vehiculeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
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
}