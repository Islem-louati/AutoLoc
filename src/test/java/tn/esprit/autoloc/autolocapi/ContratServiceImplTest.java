package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Contrat;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IContratRepository;
import tn.esprit.autoloc.autolocapi.service.ContratServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratServiceImplTest {

    @Mock
    IContratRepository repository;

    @InjectMocks
    ContratServiceImpl service;

    private Contrat contratValide() {
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.of(2026, 6, 1));
        c.setMontantTotal(new BigDecimal("500.00"));
        c.setValide(true);
        return c;
    }

    @Test
    void createEnregistreContratValide() {
        Contrat c = contratValide();
        when(repository.save(c)).thenReturn(c);
        assertSame(c, service.create(c));
    }

    @Test
    void createRefuseMontantNul() {
        Contrat c = contratValide();
        c.setMontantTotal(null);
        assertThrows(IllegalArgumentException.class, () -> service.create(c));
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
        Contrat existant = contratValide();
        existant.setIdContrat(1L);
        Contrat nouveau = contratValide();
        nouveau.setMontantTotal(new BigDecimal("900.00"));
        when(repository.findById(1L)).thenReturn(Optional.of(existant));
        when(repository.save(existant)).thenReturn(existant);

        assertEquals(new BigDecimal("900.00"), service.update(1L, nouveau).getMontantTotal());
    }
}