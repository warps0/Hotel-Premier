package edu.utn.tp_disenyo.hotel_premier.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservaDTO {
    private Long id;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
    private Long responsableId;
    private List<Long> habitacionesIds;
}