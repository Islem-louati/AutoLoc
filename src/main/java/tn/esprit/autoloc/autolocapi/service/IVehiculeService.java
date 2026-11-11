package tn.esprit.autoloc.autolocapi.service;

import tn.esprit.autoloc.autolocapi.calcul.VehiculeOffre;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;

public interface IVehiculeService extends IGenericService<Vehicule, Long> {
    double calculTarif(Long idVehicule, int jours, VehiculeOffre offre);
    Vehicule desaffecterAgence(Long id);
    Vehicule affecterAgence(Long id,Long agenceId);
    Vehicule affecterEquipement(Long idVehicule, Long idEquipement);




}