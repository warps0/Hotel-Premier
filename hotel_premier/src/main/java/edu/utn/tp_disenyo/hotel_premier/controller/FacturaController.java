package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
import edu.utn.tp_disenyo.hotel_premier.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factura")
@CrossOrigin(origins = "http://localhost:3000")
public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping("/personaJuridica/razonSocial/{razonSocial}")
    public ResponseEntity<PersonaJuridica>  getPersonaJuridicaByRazonSocial(@PathVariable("razonSocial") String razonSocial){
        return new ResponseEntity<>(facturaService.getPersonaJuridicaByRazonSocial(razonSocial), HttpStatus.OK);
    }

    @GetMapping("/personaJuridica/cuit/{cuit}")
    public ResponseEntity<PersonaJuridica>  getPersonaJuridicaByCuit(@PathVariable("cuit") String cuit){
        return new ResponseEntity<>(facturaService.getPersonaJuridicaByCuit(cuit), HttpStatus.OK);
    }

    @PostMapping("/personaJuridica")
    public ResponseEntity<PersonaJuridica> createPersonaJuridica(@RequestBody PersonaJuridica personaJuridica){
        return new ResponseEntity<>(facturaService.createPersonaJuridica(personaJuridica), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Factura> createFactura(@RequestBody FacturaCreateDTO factura){
        return new ResponseEntity<>(facturaService.createFactura(factura), HttpStatus.CREATED);
    }

    @PutMapping("/servicios/pagar/{idEstadiaServicio}")
    public ResponseEntity<EstadiaServicio> pagarServicio(@PathVariable long idEstadiaServicio) {
        return new ResponseEntity<>(facturaService.pagarServicio(idEstadiaServicio), HttpStatus.OK);
    }
}
