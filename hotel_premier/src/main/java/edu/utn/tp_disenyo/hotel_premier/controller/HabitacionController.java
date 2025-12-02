package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;

import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/habitacion")
class HabitacionController {

    private HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @PostMapping("/init")
    public ResponseEntity<String> initHabitaciones() throws Exception {
        return new ResponseEntity<>(service.initHabitaciones(), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Habitacion> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Habitacion> create(@RequestParam TipoHabitacion tipo)
    throws Exception {
        return new ResponseEntity<Habitacion>(service.create(tipo), HttpStatus.CREATED);
    }

    @GetMapping("/buscar")
    public List<HabitacionDTO> getByFecha(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        return service.getHabitacionesByRangoFecha(fechaInicio, fechaFin);
    }

    @PutMapping("/agregarEstado/{id}")
    public ResponseEntity<Habitacion> agregarEstado(@PathVariable long id, @RequestBody EstadoHabitacion estadoHabitacion)
    throws Exception {
        return new ResponseEntity<Habitacion>(service.agregarEstado(id, estadoHabitacion), HttpStatus.OK);
    }

    @PutMapping("/borrarEstado/{id}")
    public ResponseEntity<Habitacion> borrarEstado(@PathVariable long id, @RequestBody EstadoHabitacion estadoHabitacion)
            throws Exception {
        return new ResponseEntity<Habitacion>(service.borrarEstado(id, estadoHabitacion), HttpStatus.OK);
    }
}
