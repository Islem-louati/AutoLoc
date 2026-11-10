// package tn.esprit.autoloc.autolocapi.io;
package tn.esprit.autoloc.autolocapi.io;

public class RapportFactory {
    public static IRapport createRapport() {
        return new RapportConsole();
    }
}