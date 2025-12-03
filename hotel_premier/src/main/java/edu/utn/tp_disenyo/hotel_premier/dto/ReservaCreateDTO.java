package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservaCreateDTO {
    private List<Long> habitacionesIds;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
