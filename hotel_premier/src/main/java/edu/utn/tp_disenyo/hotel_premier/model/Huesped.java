package edu.utn.tp_disenyo.hotel_premier.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Huesped extends Persona {
    private String nombre;
    private String apellido;
    private String docIdentidad;
    //private EnumTipoDoc tipoDoc;

    //TODO: EnumTipoDoc, toString, comparator
}
