// package tn.esprit.autoloc.autolocapi.calcul;
package tn.esprit.autoloc.autolocapi.calcul;

import org.springframework.stereotype.Component;

@Component
public class StandardStrategy implements ITarificationStrategy {
    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * jours;
    }
    @Override
    public VehiculeOffre getOffre() { return VehiculeOffre.STANDARD; }
}