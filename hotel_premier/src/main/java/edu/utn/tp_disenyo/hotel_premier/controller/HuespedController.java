package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

import java.util.List;

@RestController
@RequestMapping("/api/huesped")
@CrossOrigin(origins = "http://localhost:3000")
public class HuespedController {

    private final HuespedService service;

    public HuespedController(HuespedService service) {
        this.service = service;
    }

    // Obtener TODOS los huéspedes
    @GetMapping
    public ResponseEntity<List<HuespedDTO>> getAll(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) TipoDoc tipoDoc) {
        return ResponseEntity.ok(service.getAll(nombre, apellido, documento, tipoDoc));
    }

    // Obtener un huésped por id
    @GetMapping("/{id}")
    public ResponseEntity<Huesped> getById(@PathVariable Long id) throws HuespedNotFoundException, EntityNotSavedException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    // Existe un huésped con el documento especificado?
    // Borrar
    @GetMapping("/existsByDocumento")
    public ResponseEntity<Boolean> existsByDocumento(@RequestParam String documento, @RequestParam TipoDoc tipoDoc) {
        return new ResponseEntity<>(service.existsByDocumento(documento, tipoDoc), HttpStatus.OK);
    }

    // Crear un huésped
    @PostMapping
    public ResponseEntity<Huesped> create(@RequestBody Huesped huesped) throws Exception {
        return new ResponseEntity<>(service.create(huesped), HttpStatus.CREATED);
    }

    // Actualizar un huésped
    @PutMapping("/{id}")
    public ResponseEntity<Huesped> update(@PathVariable Long id, @RequestBody Huesped huesped)
            throws HuespedNotFoundException {
        return new ResponseEntity<>(service.update(id, huesped), HttpStatus.OK);
    }

    // Eliminar un huésped
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HuespedNotFoundException, EntityNotSavedException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
