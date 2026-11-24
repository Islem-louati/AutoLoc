package tn.esprit.autoloc.autolocapi.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RapportConsole implements IRapport {
    private static final Logger log = LoggerFactory.getLogger(RapportConsole.class);
    private double total = 0;

    @Override
    public void afficherEntete(String entete) {
        log.info("=== {} ===", entete);
    }

    @Override
    public void afficherLigne(String ligne) {
        log.info(ligne);
    }

    @Override
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}