package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor  // 👈 ESTO ES OBLIGATORIO PARA EVITAR EL ERROR 415
@AllArgsConstructor
public class FacturaCreateDTO {
    
    private Long idResponsableDePago;
    private Long idEstadia;
    
    // Spring intentará convertir el JSON de servicios a esta lista de objetos
    private List<EstadiaServicio> servicios; 
}