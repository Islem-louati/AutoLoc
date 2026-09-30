package tn.esprit.autoloc.autolocapi;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.CategorieVehicule;
import tn.esprit.autoloc.autolocapi.domain.StatutVehicule;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock agenceRepository;
    @Test
    void addAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence Ariana");
        agence.setVille("Tunis");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("78414TUN96");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.CITADINE);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("785414TUN95");
        v2.setMarque("Toyata");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.BERLINE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setAgence(agence);

        Set<Vehicule> vehicules = new HashSet<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        agenceRepository.save(agence);
    }
    @Test
    void loadAgence() {
        StringBuilder sb = new StringBuilder();

        for (Agence agence : agenceRepository.findAll()) {
            sb.append("Agence id=").append(agence.getIdAgence())
                    .append(", nom=").append(agence.getNom())
                    .append(", nb vehicules=").append(agence.getVehicules().size())
                    .append("\n");

            for (Vehicule v : agence.getVehicules()) {
                sb.append("vehicule id=").append(v.getIdVehicule())
                        .append(", immatriculation=").append(v.getImmatriculation())
                        .append("\n");
            }
        }

        fail(sb.toString());
    }

}
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}
