package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Utils;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;

@Service
public class EquipementServiceImpl extends GenericServiceImpl<Equipement, Long> implements IEquipementService {

    public EquipementServiceImpl(IEquipementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Equipement"; }

    @Override
    protected Long getId(Equipement e) { return e.getIdEquipement(); }

    @Override
    protected void setFields(Equipement source, Equipement target) {
        target.setLibelle(source.getLibelle());
    }

    @Override
    public boolean check(Equipement e, boolean isNew) {
        return e != null && Utils.isNotEmpty(e.getLibelle());
    }
}
