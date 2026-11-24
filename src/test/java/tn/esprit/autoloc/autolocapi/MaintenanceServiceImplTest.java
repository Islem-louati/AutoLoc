package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Maintenance;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autoloc.autolocapi.service.MaintenanceServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaintenanceServiceImplTest {

    @Mock
    IMaintenanceRepository repository;

    @InjectMocks
    MaintenanceServiceImpl service;

    private Maintenance maintenanceValide() {
        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.of(2026, 3, 1));
        m.setDateFin(LocalDate.of(2026, 3, 5));
        m.setDescription("Vidange");
        return m;
    }

    @Test
    void createEnregistreMaintenanceValide() {
        Maintenance m = maintenanceValide();
        when(repository.save(m)).thenReturn(m);
        assertSame(m, service.create(m));
    }

    @Test
    void createRefuseDescriptionVide() {
        Maintenance m = maintenanceValide();
        m.setDescription("");
        assertThrows(IllegalArgumentException.class, () -> service.create(m));
    }

    @Test
    void findByIdIntrouvable() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(9L));
    }

    @Test
    void deleteByIdIntrouvable() {
        when(repository.existsById(9L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(9L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void updateCopieLesChamps() {
        Maintenance existante = maintenanceValide();
        existante.setIdMaintenance(1L);
        Maintenance nouvelle = maintenanceValide();
        nouvelle.setDescription("Pneus");
        when(repository.findById(1L)).thenReturn(Optional.of(existante));
        when(repository.save(existante)).thenReturn(existante);

        assertEquals("Pneus", service.update(1L, nouvelle).getDescription());
    }
}