package edu.utn.tp_disenyo.hotel_premier.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaServicioDTO;
import edu.utn.tp_disenyo.hotel_premier.service.EstadiaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/estadia")
@CrossOrigin(origins = "http://localhost:3000")
public class EstadiaController {
    private final EstadiaService estadiaService;

    public EstadiaController(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @GetMapping("/{idEstadia}")
    public EstadiaDTO getEstadiaById(@PathVariable Long idEstadia) {
        EstadiaDTO esDTO = new EstadiaDTO(estadiaService.findEstadiaById(idEstadia));
        return esDTO;
    }

    @GetMapping("/servicios/{idEstadia}")
    public List<EstadiaServicioDTO> getServicios(@PathVariable Long idEstadia) {
        return estadiaService.findEstadiaServicioByIdEstadia(idEstadia);
    }
    
}
