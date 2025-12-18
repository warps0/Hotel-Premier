package edu.utn.tp_disenyo.hotel_premier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefono;
    private String correo;
    private String domicilio; // Refactorizamos nombre para mejor comprensión vs 'dirección'
    private String pais;
    private String localidad;

    @PrePersist
    @PreUpdate
    public void ensureUppercase() {
        if (this.telefono != null) {
            this.telefono = this.telefono.toUpperCase();
        }
        if (this.correo != null) {
            this.correo = this.correo.toUpperCase();
        }
        if (this.domicilio != null) {
            this.domicilio = this.domicilio.toUpperCase();
        }
        if (this.pais != null) {
            this.pais = this.pais.toUpperCase();
        }
        if (this.localidad != null) {
            this.localidad = this.localidad.toUpperCase();
        }
    }
}
