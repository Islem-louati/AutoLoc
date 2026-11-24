package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.domain.Reservation;
import tn.esprit.autoloc.autolocapi.domain.StatutReservation;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IReservationRepository;
import tn.esprit.autoloc.autolocapi.service.ReservationServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    IReservationRepository repository;

    @InjectMocks
    ReservationServiceImpl service;

    private Reservation reservationValide() {
        Reservation r = new Reservation();
        r.setDateDebut(LocalDate.of(2026, 6, 1));
        r.setDateFin(LocalDate.of(2026, 6, 10));
        r.setStatut(StatutReservation.EN_ATTENTE);
        return r;
    }

    @Test
    void createEnregistreReservationValide() {
        Reservation r = reservationValide();
        when(repository.save(r)).thenReturn(r);
        assertSame(r, service.create(r));
    }

    @Test
    void createRefuseDateFinAvantDateDebut() {
        Reservation r = reservationValide();
        r.setDateFin(LocalDate.of(2026, 5, 1));
        assertThrows(IllegalArgumentException.class, () -> service.create(r));
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
        Reservation existante = reservationValide();
        existante.setIdReservation(1L);
        Reservation nouvelle = reservationValide();
        nouvelle.setStatut(StatutReservation.CONFIRMEE);
        when(repository.findById(1L)).thenReturn(Optional.of(existante));
        when(repository.save(existante)).thenReturn(existante);

        assertEquals(StatutReservation.CONFIRMEE, service.update(1L, nouvelle).getStatut());
    }
}