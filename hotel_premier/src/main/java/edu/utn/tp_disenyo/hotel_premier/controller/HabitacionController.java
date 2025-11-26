package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/habitacion")
class HabitacionController {

    private HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Habitacion> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Habitacion> create(@RequestBody Habitacion habitacion) throws Exception {
        System.out.println(habitacion);
        return new ResponseEntity<Habitacion>(service.create(habitacion), HttpStatus.CREATED);
    }

    @GetMapping("/buscar")
    public List<HabitacionDTO> getByFecha(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        return service.getHabitacionesByRangoFecha(fechaInicio, fechaFin);
    }

}
