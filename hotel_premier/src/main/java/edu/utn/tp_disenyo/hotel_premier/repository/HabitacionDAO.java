package edu.utn.tp_disenyo.hotel_premier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;

public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {
    
}
