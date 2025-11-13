package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDate;

import jakarta.persistence.*;
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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "contactoId")
    private Contacto mediosDeContacto; // List ES UNA INTERFAZ, dónde decimos qué implementación? (Probablemente en el DAO)
    
}
