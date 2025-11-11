package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

import java.util.List;

@RestController
@RequestMapping("/huesped")
public class HuespedController {

    private final HuespedService service;

    @Autowired
    public HuespedController(HuespedService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Huesped>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Huesped> getById(@PathVariable Long id) throws HuespedNotFoundException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Huesped> create(@ModelAttribute Huesped huesped) throws Exception {
        return new ResponseEntity<>(service.create(huesped), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Huesped> update(@PathVariable Long id, @RequestBody Huesped huesped) throws HuespedNotFoundException {
        return new ResponseEntity<>(service.update(id, huesped), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws HuespedNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
