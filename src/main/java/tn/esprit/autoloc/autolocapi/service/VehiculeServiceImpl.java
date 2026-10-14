package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Utils;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    public VehiculeServiceImpl(IVehiculeRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Vehicule"; }

    @Override
    protected Long getId(Vehicule v) { return v.getIdVehicule(); }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule v, boolean isNew) {
        return v != null
                && Utils.isNotEmpty(v.getImmatriculation())
                && Utils.isNotEmpty(v.getMarque())
                && Utils.isNotEmpty(v.getModele())
                && v.getCategorie() != null
                && v.getStatut() != null
                && v.getTarifJournalier() != null;
    }
}
