package edu.utn.tp_disenyo.hotel_premier.model;

import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Huesped extends Persona {
    private String nombre;
    private String apellido;
    private String docIdentidad;
    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDoc;

    // Comparar dos huéspedes por documento (tipoDocumento a agregar coming soon)
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(docIdentidad, huesped.docIdentidad);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(docIdentidad);
    }

    @PrePersist
    @PreUpdate
    public void ensureUppercase() {
        if (this.nombre != null) {
            this.nombre = this.nombre.toUpperCase();
        }
        if (this.apellido != null) {
            this.apellido = this.apellido.toUpperCase();
        }
        if (this.docIdentidad != null) {
            this.docIdentidad = this.docIdentidad.toUpperCase();
        }
    }
}
