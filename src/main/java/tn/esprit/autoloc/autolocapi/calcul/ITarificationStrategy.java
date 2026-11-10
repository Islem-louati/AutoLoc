// package tn.esprit.autoloc.autolocapi.calcul;
package tn.esprit.autoloc.autolocapi.calcul;

public interface ITarificationStrategy {
    double calculer(double tarifJournalier, int jours);
    VehiculeOffre getOffre();
}