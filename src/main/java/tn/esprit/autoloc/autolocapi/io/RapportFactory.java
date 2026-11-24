package tn.esprit.autoloc.autolocapi.io;

public class RapportFactory {

    // 1. Ajoutez ce constructeur privé
    private RapportFactory() {
        throw new UnsupportedOperationException("Cette classe utilitaire ne doit pas être instanciée.");
    }

    // 2. Gardez votre méthode statique existante
    public static IRapport createRapport() {
        return new RapportConsole();
    }
}