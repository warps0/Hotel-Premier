package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadiaServicioDAO extends JpaRepository<EstadiaServicio, Long> {
    // public EstadiaServicio findByEstadia(Long idEstadia);
    public List<EstadiaServicio> findAllByEstadiaId(Long idEstadia);
}