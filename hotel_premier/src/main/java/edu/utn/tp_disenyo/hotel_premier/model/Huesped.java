package edu.utn.tp_disenyo.hotel_premier.model;

import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
