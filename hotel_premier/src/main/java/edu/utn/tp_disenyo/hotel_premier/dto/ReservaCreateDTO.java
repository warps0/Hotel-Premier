package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservaCreateDTO {
    private List<Long> huespedesIds;
    private List<Long> habitacionesIds;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private String nombre;
    private String apellido;
    private String contacto;
}
