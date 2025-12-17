package edu.utn.tp_disenyo.hotel_premier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Persona;

public interface PersonaDAO extends JpaRepository<Persona, Long> {

}
