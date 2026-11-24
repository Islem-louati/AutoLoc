package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Employe;
import tn.esprit.autoloc.autolocapi.domain.RoleEmploye;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IEmployeRepository;
import tn.esprit.autoloc.autolocapi.service.EmployeServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceImplTest {

    @Mock
    IEmployeRepository repository;

    @InjectMocks
    EmployeServiceImpl service;

    private Employe employeValide() {
        Employe e = new Employe();
        e.setNom("Trabelsi");
        e.setPrenom("Leila");
        e.setRole(RoleEmploye.AGENT);
        return e;
    }

    @Test
    void createEnregistreEmployeValide() {
        Employe e = employeValide();
        when(repository.save(e)).thenReturn(e);
        assertSame(e, service.create(e));
    }

    @Test
    void createRefuseDonneesInvalides() {
        Employe e = employeValide();
        e.setNom("");
        assertThrows(IllegalArgumentException.class, () -> service.create(e));
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
        Employe existant = employeValide();
        existant.setIdEmploye(1L);
        Employe nouveau = employeValide();
        nouveau.setRole(RoleEmploye.MANAGER);
        when(repository.findById(1L)).thenReturn(Optional.of(existant));
        when(repository.save(existant)).thenReturn(existant);

        assertEquals(RoleEmploye.MANAGER, service.update(1L, nouveau).getRole());
    }
}