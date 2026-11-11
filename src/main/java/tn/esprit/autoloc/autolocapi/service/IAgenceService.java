package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Agence;
import tn.esprit.autoloc.autolocapi.domain.Employe;

import java.util.List;

public interface IAgenceService extends IGenericService<Agence, Long> {

    boolean hasVehicules(Long id);
    String simuler(Long idAgence, int jours, VehiculeOffre offre);
    Agence addEmployesToAgence(Long idAgence, List<Employe> employes);


}