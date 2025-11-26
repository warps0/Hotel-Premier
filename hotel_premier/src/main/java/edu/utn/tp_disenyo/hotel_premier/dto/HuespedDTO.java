package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HuespedDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String docIdentidad;
    private TipoDoc tipoDoc;
}
