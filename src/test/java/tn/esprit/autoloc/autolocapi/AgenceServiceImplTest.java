package tn.esprit.autoloc.autolocapi;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.service.AgenceServiceImpl;
import tn.esprit.autoloc.autolocapi.service.IEmployeService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgenceServiceImplTest {

    @Mock
    IAgenceRepository repository;

    @Mock
    IEmployeService employeService;

    @InjectMocks
    AgenceServiceImpl service;

    private Agence agenceValide() {
        Agence a = new Agence();
        a.setNom("Agence Test");
        a.setVille("Tunis");
        a.setAdresse("1 Rue Test");
        a.setTelephone("71000000");
        return a;
    }

    @Test
    void createRefuseIdRenseigne() {
        Agence a = agenceValide();
        a.setIdAgence(12L);
        assertThrows(IllegalArgumentException.class, () -> service.create(a));
        verify(repository, never()).save(any());
    }

    @Test
    void createRefuseDonneesInvalides() {
        Agence a = agenceValide();
        a.setNom(" ");
        assertThrows(IllegalArgumentException.class, () -> service.create(a));
    }

    @Test
    void createEnregistreAgenceValide() {
        Agence a = agenceValide();
        when(repository.save(a)).thenReturn(a);
        assertSame(a, service.create(a));
    }

    @Test
    void findByIdIntrouvable() {
        when(repository.findById(42L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(42L));
    }

    @Test
    void deleteByIdIntrouvable() {
        when(repository.existsById(42L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(42L));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void updateCopieLesChamps() {
        Agence existante = agenceValide();
        existante.setIdAgence(1L);
        Agence nouvelle = agenceValide();
        nouvelle.setNom("Nouveau nom");
        when(repository.findById(1L)).thenReturn(Optional.of(existante));
        when(repository.save(existante)).thenReturn(existante);

        assertEquals("Nouveau nom", service.update(1L, nouvelle).getNom());
    }

    @Test
    void hasVehiculesVraiSiListeNonVide() {
        Agence a = agenceValide();
        a.getVehicules().add(new Vehicule());
        when(repository.findById(1L)).thenReturn(Optional.of(a));
        assertTrue(service.hasVehicules(1L));
    }
}