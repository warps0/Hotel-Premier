package edu.utn.tp_disenyo.hotel_premier.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Contacto {
    private String telefono;
    private String correo;
    private String domicilio; // Refactorizamos nombre para mejor comprensión vs 'dirección'

    //TODO: toString, comparator
}
