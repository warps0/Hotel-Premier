package edu.utn.tp_disenyo.hotel_premier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadiaDAO extends JpaRepository<Estadia, Long> {

}
