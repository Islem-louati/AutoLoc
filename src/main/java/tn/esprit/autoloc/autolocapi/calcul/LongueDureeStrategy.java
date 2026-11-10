// package tn.esprit.autoloc.autolocapi.calcul;
package tn.esprit.autoloc.autolocapi.calcul;

import org.springframework.stereotype.Component;

@Component
public class LongueDureeStrategy implements ITarificationStrategy {
    @Override
    public double calculer(double tarifJournalier, int jours) {
        double total = tarifJournalier * 0.75 * jours;
        if (total > 2000) {
            total = total * 0.95;
        }
        return total;
    }
    @Override
    public VehiculeOffre getOffre() { return VehiculeOffre.LONGUE_DUREE; }
}