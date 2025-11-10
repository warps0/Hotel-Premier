package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ArrayList;
//import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String posIva;
    private String nacionalidad;
    private String ocupacion;
    private LocalDate fechaNacimiento;
    @ManyToOne
    @JoinColumn(name = "contactoId")
    private Contacto mediosDeContacto; // List ES UNA INTERFAZ, dónde decimos qué implementación? (Probablemente en el DAO)
    
    //TODO: lista contactos, comparator
}
