package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;

@Service
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    public PaiementServiceImpl(IPaiementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Paiement"; }

    @Override
    protected Long getId(Paiement p) { return p.getIdPaiement(); }

    @Override
    protected void setFields(Paiement source, Paiement target) {
        // inutilisé : le paiement est en lecture seule
    }

    @Override
    public Paiement create(Paiement entity) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }

    @Override
    public Paiement update(Long id, Paiement entity) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Le paiement est en lecture seule");
    }
}