// package tn.esprit.autoloc.autolocapi.io;
package tn.esprit.autoloc.autolocapi.io;

public class RapportConsole implements IRapport {
    private double total = 0;

    @Override
    public void afficherEntete(String entete) {
        System.out.println("=== " + entete + " ===");
    }

    @Override
    public void afficherLigne(String ligne) {
        System.out.println(ligne);
    }

    @Override
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}