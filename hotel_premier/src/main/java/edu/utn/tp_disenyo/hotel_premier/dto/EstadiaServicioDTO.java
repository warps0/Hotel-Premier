package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaServicioDTO {
    private Long id;
    private Long estadiaId;
    private Servicio servicio;
    private boolean incluido;

    //OJO JACKSONBACKREFERENCE EN SERVICIO
}
