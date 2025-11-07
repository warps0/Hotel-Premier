package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Persona { // Si no es @Entity, sirve igual? NO

    private String posIva;
    private String nacionalidad;
    private String ocupacion;
    private LocalDate fechaNacimiento;
    //private List<Contacto> mediosDeContacto;
    
    //TODO: lista contactos, toString, comparator
}
