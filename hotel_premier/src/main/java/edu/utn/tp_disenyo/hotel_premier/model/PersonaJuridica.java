package edu.utn.tp_disenyo.hotel_premier.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonaJuridica extends Persona {
    private String razonSocial; //Nombre

    //TODO: toString, comparator
}
