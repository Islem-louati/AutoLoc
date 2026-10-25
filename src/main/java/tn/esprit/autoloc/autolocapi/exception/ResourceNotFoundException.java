package tn.esprit.autoloc.autolocapi.exception;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String ressource) {
        super(ressource + " introuvable avec l'identifiant " + id);
    }
}

