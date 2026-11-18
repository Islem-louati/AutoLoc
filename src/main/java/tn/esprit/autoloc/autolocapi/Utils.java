package tn.esprit.autoloc.autolocapi;

public final class Utils {

    private Utils() {
        // classe utilitaire : ne pas instancier
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}