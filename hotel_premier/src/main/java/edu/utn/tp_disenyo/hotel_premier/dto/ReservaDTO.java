package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservaDTO {
    private Long id;
    private EstadoReserva estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<HuespedDTO> huespedes;
    private String nombre;
    private String apellido;
    private String contacto;
    private List<Long> habitacionesIds = new ArrayList<>();

    public ReservaDTO(Reserva reserva, List<Long> idsHabitaciones, List<HuespedDTO> huespedes) {
        this.id = reserva.getId();
        this.estado = reserva.getEstado();
        this.fechaInicio = reserva.getFechaInicio();
        this.huespedes = huespedes;
        this.fechaFin = reserva.getFechaFin();
        this.nombre = reserva.getNombre();
        this.apellido = reserva.getApellido();
        this.contacto = reserva.getContacto();
        this.habitacionesIds = idsHabitaciones;

    }
}