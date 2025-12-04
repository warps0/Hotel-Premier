package edu.utn.tp_disenyo.hotel_premier.controller;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;

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

}
