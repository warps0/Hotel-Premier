package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
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
    private Long responsableId;
    private List<Long> habitacionesIds = new ArrayList<Long>();

    public ReservaDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.estado = reserva.getEstado();
        this.fechaInicio = reserva.getFechaInicio();
        this.fechaFin = reserva.getFechaFin();
        this.responsableId = reserva.getResponsable().getId();

        for(Habitacion h : reserva.getHabitaciones()){
            habitacionesIds.add(h.getId());
        }
    }
}