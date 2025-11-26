package edu.utn.tp_disenyo.hotel_premier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long> {
    
}
