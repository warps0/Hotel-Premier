package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservaCreateDTO {
    private Long responsableId;
    private List<Long> habitacionesIds;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EstadoReserva estado;
}
