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
    private List<Long> habitacionesIds = new ArrayList<>();

    public ReservaDTO(Reserva reserva, List<Long> idsHabitaciones) {
        this.id = reserva.getId();
        this.estado = reserva.getEstado();
        this.fechaInicio = reserva.getFechaInicio();
        this.fechaFin = reserva.getFechaFin();
        this.habitacionesIds = idsHabitaciones;
    }
}