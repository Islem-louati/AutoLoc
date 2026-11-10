package tn.esprit.autoloc.autolocapi.io;

public interface IRapport {
    void afficherEntete(String entete);
    void afficherLigne(String ligne);
    double getTotal();
}