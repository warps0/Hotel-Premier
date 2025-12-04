package edu.utn.tp_disenyo.hotel_premier.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.service.ReservaService;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/reserva")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservaController {
    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<ReservaDTO> getAll(@RequestParam(required = false) EstadoReserva estadoReserva)
    throws Exception {
        if(estadoReserva == null) {
           return reservaService.getAll();
        }
        // TODO: GetByEstadoReserva
        return reservaService.getAll();
    }
    
    @PostMapping
    public ReservaDTO create(@RequestBody ReservaCreateDTO reserva)
    throws Exception {
        return reservaService.create(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> agregarHuesped(@PathVariable Long id, @RequestBody List<HuespedDTO> huespedes) throws Exception {
        return new ResponseEntity<>(reservaService.agregarHuesped(id, huespedes), HttpStatus.OK);
    }
    
}
