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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String posIva;
    private String nacionalidad;
    private String ocupacion;
    private LocalDate fechaNacimiento;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactoId")
    private Contacto mediosDeContacto;
    
    @PrePersist
    @PreUpdate
    public void ensureUppercase() {
        if (this.posIva != null) {
            this.posIva = this.posIva.toUpperCase();
        }
        if (this.nacionalidad != null) {
            this.nacionalidad = this.nacionalidad.toUpperCase();
        }
        if (this.ocupacion != null) {
            this.ocupacion = this.ocupacion.toUpperCase();
        }
    }
}
