package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors; // Importante

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

    // --- CAMBIO AQUÍ ---
    // Ya no es List<EstadiaServicio>, ahora es el DTO
    private List<EstadiaServicioDTO> servicios; 
    
    public EstadiaDTO(Estadia e) {
        this.id = e.getId();
        this.fechaIngreso = e.getFechaIngreso();
        this.fechaEgreso = e.getFechaEgreso();

        this.reservaId = e.getReserva() != null ? e.getReserva().getId() : null;
        this.habitacionId = e.getHabitacionId(); // O e.getHabitacion().getId()

        this.huespedes = e.getHuespedes() != null ? 
            e.getHuespedes().stream().map(HuespedDTO::new).collect(Collectors.toList()) : 
            new ArrayList<>();
        
        // --- CAMBIO EN EL MAPEO ---
        // Convertimos la lista de entidades a lista de DTOs
        if (e.getServicios() != null) {
            this.servicios = e.getServicios()
                .stream()
                .map(EstadiaServicioDTO::new) // Usamos el constructor del paso 2
                .collect(Collectors.toList());
        } else {
            this.servicios = new ArrayList<>();
        }
    }
}