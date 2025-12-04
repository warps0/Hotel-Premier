package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Estadia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
public class EstadiaDTO {

    private Long id;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;

    private Long reservaId;
    private Long habitacionId;

    private List<HuespedDTO> huespedes;

    public EstadiaDTO(Estadia e) {
        this.id = e.getId();
        this.fechaIngreso = e.getFechaIngreso();
        this.fechaEgreso = e.getFechaEgreso();

        this.reservaId = e.getReserva() != null ? e.getReserva().getId() : null;
        this.habitacionId = e.getHabitacionId();

        this.huespedes =
            e.getHuespedes()
             .stream()
             .map(HuespedDTO::new)
             .toList();
    }
}
