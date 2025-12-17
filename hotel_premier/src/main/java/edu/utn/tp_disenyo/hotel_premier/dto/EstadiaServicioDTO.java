package edu.utn.tp_disenyo.hotel_premier.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;

@Data
@NoArgsConstructor
public class EstadiaServicioDTO {
    private Long id;
    private Boolean incluido;
    
    // Aquí es donde metemos el objeto enriquecido
    private ServicioDTO servicio; 

    // Constructor que convierte de Entidad a DTO
    public EstadiaServicioDTO(EstadiaServicio entidad) {
        this.id = entidad.getId();
        this.incluido = entidad.isIncluido();
        
        if (entidad.getServicio() != null) {
            // Asumiendo que ya tienes un ServicioDTO básico (id, nombre, precio)
            // Si no lo tienes, créalo o usa getters manuales aquí
            this.servicio = new ServicioDTO(); 
            this.servicio.setId(entidad.getServicio().getId());
            this.servicio.setTipoServicio(entidad.getServicio().getTipoServicio()); 		// O getTipoServicio()
            this.servicio.setCostoTotal(entidad.getServicio().getCostoTotal()); 		// O getCosto()
            
        }
    }
}