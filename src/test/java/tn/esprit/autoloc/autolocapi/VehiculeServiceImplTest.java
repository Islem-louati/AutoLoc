package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.autolocapi.calcul.ITarificationStrategy;
import tn.esprit.autoloc.autolocapi.domain.*;
import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autoloc.autolocapi.repository.IAgenceRepository;
import tn.esprit.autoloc.autolocapi.repository.IEquipementRepository;
import tn.esprit.autoloc.autolocapi.repository.IVehiculeRepository;
import tn.esprit.autoloc.autolocapi.service.VehiculeServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceImplTest {

    @Mock
    private IVehiculeRepository repository;

    @Mock
    private IAgenceRepository agenceRepository;

    @Mock
    private IEquipementRepository equipementRepository;

    private VehiculeServiceImpl service;

    @BeforeEach
    void setUp() {
        // On instancie le service avec les Repositories attendus par le constructeur
        // et une liste vide pour les stratégies (non testées ici)
        List<ITarificationStrategy> strategies = new ArrayList<>();
        service = new VehiculeServiceImpl(repository, strategies, agenceRepository, equipementRepository);
    }

    private Vehicule vehiculeValide() {
        Vehicule v = new Vehicule();
        v.setImmatriculation("123TUN1");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setStatut(StatutVehicule.DISPONIBLE);
        v.setTarifJournalier(new BigDecimal("60.00"));
        return v;
    }

    @Test
    void createRefuseDonneesInvalides() {
        Vehicule v = vehiculeValide();
        v.setMarque(""); // Donnée invalide
        assertThrows(IllegalArgumentException.class, () -> service.create(v));
    }

    @Test
    void findByIdIntrouvable() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(9L));
    }

    @Test
    void desaffecterAgenceMetAgenceANull() {
        Vehicule v = vehiculeValide();
        v.setAgence(new Agence()); // On lui donne une agence au départ
        when(repository.findById(1L)).thenReturn(Optional.of(v));

        service.desaffecterAgence(1L);

        assertNull(v.getAgence());
    }

    @Test
    void affecterAgenceAssocieLAgence() {
        Vehicule v = vehiculeValide();
        Agence a = new Agence();
        a.setNom("Agence Test");

        when(repository.findById(1L)).thenReturn(Optional.of(v));
        // On mock le repository de l'agence (pas le service)
        when(agenceRepository.findById(2L)).thenReturn(Optional.of(a));

        service.affecterAgence(1L, 2L);

        assertSame(a, v.getAgence());
    }

    @Test
    void affecterEquipementAjouteDesDeuxCotes() {
        Vehicule v = vehiculeValide();
        Equipement e = new Equipement();
        e.setLibelle("GPS");

        when(repository.findById(1L)).thenReturn(Optional.of(v));
        // On mock le repository de l'équipement
        when(equipementRepository.findById(3L)).thenReturn(Optional.of(e));

        service.affecterEquipement(1L, 3L);

        assertTrue(v.getEquipements().contains(e));
        assertTrue(e.getVehicules().contains(v));
    }
}