package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HabitacionDTO {
    private Long id;
    private TipoHabitacion tipoHabitacion;
    //TODO: Ordenado por fechaInicio
    private List<EstadoHabitacion> historialEstado = new ArrayList<>();
}
