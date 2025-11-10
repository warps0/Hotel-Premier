package edu.utn.tp_disenyo.hotel_premier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "huesped")
public class Huesped extends Persona {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
*/
    private String nombre;
    private String apellido;
    private String docIdentidad;
    //private EnumTipoDoc tipoDoc;

    //TODO: Contacto, EnumTipoDoc, comparator
}
