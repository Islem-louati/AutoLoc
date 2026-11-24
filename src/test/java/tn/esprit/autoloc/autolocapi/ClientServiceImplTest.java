package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Client;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IClientRepository;
import tn.esprit.autoloc.autolocapi.service.ClientServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    IClientRepository repository;

    @InjectMocks
    ClientServiceImpl service;

    private Client clientValide() {
        Client c = new Client();
        c.setNom("Ben Ali");
        c.setPrenom("Sami");
        c.setEmail("sami@test.tn");
        c.setTelephone("20000000");
        c.setNumPermis("P123");
        c.setDateInscription(LocalDate.of(2026, 1, 1));
        return c;
    }

    @Test
    void createEnregistreClientValide() {
        Client c = clientValide();
        when(repository.save(c)).thenReturn(c);
        assertSame(c, service.create(c));
    }

    @Test
    void createRefuseDonneesInvalides() {
        Client c = clientValide();
        c.setEmail(" ");
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
}