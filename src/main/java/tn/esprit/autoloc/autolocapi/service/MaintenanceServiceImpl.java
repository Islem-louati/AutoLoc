package tn.esprit.autoloc.autolocapi.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.autolocapi.Utils;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Maintenance"; }

    @Override
    protected Long getId(Maintenance m) { return m.getIdMaintenance(); }

    @Override
    protected void setFields(Maintenance source, Maintenance target) {
        target.setDateDebut(source.getDateDebut());
        target.setDateFin(source.getDateFin());
        target.setDescription(source.getDescription());
        target.setVehicule(source.getVehicule());
    }

    @Override
    public boolean check(Maintenance m, boolean isNew) {
        return m != null
                && m.getDateDebut() != null
                && m.getDateFin() != null
                && !m.getDateFin().isBefore(m.getDateDebut())
                && Utils.isNotEmpty(m.getDescription());
    }
}
