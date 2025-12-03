package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/habitacion")
@CrossOrigin(origins = "http://localhost:3000")
class HabitacionController {

    private HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    // Crear TODAS las habitaciones
    @PostMapping("/init")
    public ResponseEntity<String> initHabitaciones() throws Exception {
        return new ResponseEntity<>(habitacionService.initHabitaciones(), HttpStatus.CREATED);
    }

    // Obtener TODAS las habitaciones
    @GetMapping
    public List<Habitacion> getAll(@RequestParam(required = false) TipoHabitacion tipo) {
        if(tipo == null) {
            return habitacionService.getAll();
        }
        else return habitacionService.findByTipoHabitacion(tipo);
    }

    // Buscar habitaciones por rango de fechas (fechaInicio ~ fechaFin)
    @GetMapping("/buscar")
    public List<HabitacionDTO> getByFecha(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        return service.getHabitacionesByRangoFecha(fechaInicio, fechaFin);
    }

    // Crear un huésped
    @PostMapping
    public ResponseEntity<Habitacion> create(@RequestParam TipoHabitacion tipo)
    throws Exception {
        if (tipo == null) {
            //TODO: Mas exceptions
            throw new Exception();
        }
        return new ResponseEntity<Habitacion>(habitacionService.create(tipo), HttpStatus.CREATED);
    }

    // Agregar una instancia de EstadoHabitacion a una Habitación
    @PutMapping("/agregarEstado/{id}")
    public ResponseEntity<Habitacion> agregarEstado(@PathVariable long id, @RequestBody EstadoHabitacion estadoHabitacion)
    throws Exception {
        return new ResponseEntity<Habitacion>(habitacionService.agregarEstado(id, estadoHabitacion), HttpStatus.OK);
    }

    // Borrar una instancia de EstadoHabitacion a una Habitación
    @PutMapping("/borrarEstado/{id}")
    public ResponseEntity<Habitacion> borrarEstado(@PathVariable long id, @RequestBody EstadoHabitacion estadoHabitacion)
            throws Exception {
        return new ResponseEntity<Habitacion>(habitacionService.borrarEstado(id, estadoHabitacion), HttpStatus.OK);
    }

}
