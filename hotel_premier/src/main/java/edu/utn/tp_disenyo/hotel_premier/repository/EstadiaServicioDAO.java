package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadiaServicioDAO extends JpaRepository<EstadiaServicio, Long> {
    // public EstadiaServicio findByEstadia(Long idEstadia);
    public List<EstadiaServicio> findAllByEstadia(Long idEstadia);
}