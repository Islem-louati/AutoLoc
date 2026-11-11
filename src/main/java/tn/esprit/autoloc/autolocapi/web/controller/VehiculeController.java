package tn.esprit.autoloc.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.autolocapi.domain.Vehicule;
import tn.esprit.autoloc.autolocapi.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des véhicules")
public class VehiculeController {

    private final IVehiculeService service;
    private final IVehiculeService iVehiculeService;

    @Operation(summary = "Lister les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste retournée")
    @GetMapping
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("{id}")
    public Vehicule findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.create(vehicule);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @PutMapping("{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return service.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Operation(summary = "Désaffecter l'agence d'un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule désaffecté")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @PutMapping("{id}/desaffecter-agence")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Vehicule desaffecterAgence(@PathVariable Long id) {
        return iVehiculeService.desaffecterAgence(id);
    }
    @Operation(summary = "Affecter une agence à un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule affecté")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @PutMapping("{id}/affecter-agence/{agenceId}") // <-- AJOUTER /{agenceId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Vehicule affecterAgence(@PathVariable Long id, @PathVariable Long agenceId) {
        return iVehiculeService.affecterAgence(id, agenceId);
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @PutMapping("{id}/affecter-equipement/{equipementId}")
    public Vehicule affecterEquipement(@PathVariable Long id, @PathVariable Long equipementId) {
        return service.affecterEquipement(id, equipementId);
    }
}