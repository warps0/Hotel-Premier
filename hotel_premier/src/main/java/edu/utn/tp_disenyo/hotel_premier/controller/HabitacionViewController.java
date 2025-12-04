package edu.utn.tp_disenyo.hotel_premier.controller;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
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

    @GetMapping("/habitaciones/ocupar")
    public String reservarHabitacion(Model model) {
        return "ocuparHabitacion";
    }

    @GetMapping("/confirmar/reserva")
    public String mostrarConfirmacionReserva() {
        return "confirmarReserva"; // nombre del archivo HTML sin extensión
    }

    // @PostMapping("/api/reservas")
    // @ResponseBody
    // public ResponseEntity<?> guardarReserva(@RequestBody ReservaDTO reserva) {
    // try {
    //     // Lógica para guardar en la base de datos
    //     reservaService.guardarReserva(reserva);
    //     return ResponseEntity.ok().body(Map.of("mensaje", "Reserva guardada exitosamente"));
    // } catch (Exception e) {
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
    //     }
    // }
}
