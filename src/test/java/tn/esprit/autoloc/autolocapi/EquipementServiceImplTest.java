package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Equipement;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.service.EquipementServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipementServiceImplTest {

    @Mock
    IEquipementRepository repository;

    @InjectMocks
    EquipementServiceImpl service;

    private Equipement equipementValide() {
        Equipement e = new Equipement();
        e.setLibelle("GPS");
        return e;
    }

    @Test
    void createEnregistreEquipementValide() {
        Equipement e = equipementValide();
        when(repository.save(e)).thenReturn(e);
        assertSame(e, service.create(e));
    }

    @Test
    void createRefuseLibelleVide() {
        Equipement e = equipementValide();
        e.setLibelle("");
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
    void updateCopieLeLibelle() {
        Equipement existant = equipementValide();
        existant.setIdEquipement(1L);
        Equipement nouveau = new Equipement();
        nouveau.setLibelle("Siege bebe");
        when(repository.findById(1L)).thenReturn(Optional.of(existant));
        when(repository.save(existant)).thenReturn(existant);

        assertEquals("Siege bebe", service.update(1L, nouveau).getLibelle());
    }
}