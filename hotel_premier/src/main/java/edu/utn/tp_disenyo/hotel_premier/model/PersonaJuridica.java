package edu.utn.tp_disenyo.hotel_premier.model;

import jakarta.persistence.*;
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
public class PersonaJuridica extends Persona {
    private String razonSocial; //Nombre

    // Dos personas jurídicas son iguales si tienen la misma razón social
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonaJuridica that = (PersonaJuridica) o;
        return Objects.equals(razonSocial, that.razonSocial);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(razonSocial);
    }
}
