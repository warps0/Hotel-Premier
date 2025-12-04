package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;

public class EstadiaDTO {

    private Long id;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;
    private Reserva reserva;
    private List<HuespedDTO> huespedes;
    private Long habitacionId;

    public EstadiaDTO(Estadia e) {
        this.id = e.getId();
        this.fechaIngreso = e.getFechaIngreso();
        this.fechaEgreso = e.getFechaEgreso();
        this.reserva = e.getReserva();

        for(Huesped h : e.getHuespedes()) {
            huespedes.add(new HuespedDTO(h));
        }

        this.habitacionId = e.getHabitacionId();
    }
}
