package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import edu.utn.tp_disenyo.hotel_premier.model.Servicio;

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
    private List<EstadiaServicio> servicios;
    

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
        
        this.servicios = e.getServicios();
    }
}
