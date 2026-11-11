package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.domain.Paiement;
import tn.esprit.autoloc.autolocapi.repository.IPaiementRepository;

import java.util.List;

@Service
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository repository;

    public PaiementServiceImpl(IPaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean check(Paiement entity, boolean isNew) {
        return entity != null;
    }

    @Override
    public Paiement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException("Paiement", id.toString()));
    }

    @Override
    public List<Paiement> findAll() {
        return repository.findAll();
    }
}