// package tn.esprit.autoloc.autolocapi.calcul;
package tn.esprit.autoloc.autolocapi.calcul;

import org.springframework.stereotype.Component;

@Component
public class WeekendStrategy implements ITarificationStrategy {
    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * 0.80 * jours;
    }
    @Override
    public VehiculeOffre getOffre() { return VehiculeOffre.WEEKEND; }
}