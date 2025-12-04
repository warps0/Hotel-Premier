package edu.utn.tp_disenyo.hotel_premier.controller;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;

@Controller
@Primary
public class HabitacionViewController {
  
    HabitacionService habitacionService;
    
    public HabitacionViewController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping("/habitaciones/estado")
    public String mostrarEstadoHabitaciones(Model model) {
        return "verEstadoHabitaciones"; 
}

    @GetMapping("/habitaciones/reservar")
    public String ocuparHabitacion(Model model) {
        return "reservarHabitacion"; 
    }

    @GetMapping("/habitaciones/ocupar")
    public String reservarHabitacion(Model model) {
        return "ocuparHabitacion"; 
    }
}
