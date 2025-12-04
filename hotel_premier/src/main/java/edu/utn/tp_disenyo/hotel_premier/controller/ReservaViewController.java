package edu.utn.tp_disenyo.hotel_premier.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import edu.utn.tp_disenyo.hotel_premier.service.ReservaService;

@Controller
public class ReservaViewController {

    ReservaService reservaService;

    public ReservaViewController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/habitaciones/ocupar")
    public String reservarHabitacion(Model model) {
        return "ocuparHabitacion";
    }
    
}
