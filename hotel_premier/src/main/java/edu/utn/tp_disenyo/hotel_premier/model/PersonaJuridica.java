package edu.utn.tp_disenyo.hotel_premier.model;

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
@Table(name = "persona_juridica")
public class PersonaJuridica extends Persona {

   // @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   // private Long id;
    private String razonSocial; //Nombre

    //TODO: comparator
}
