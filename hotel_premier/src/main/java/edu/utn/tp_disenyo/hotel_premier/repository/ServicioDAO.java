package edu.utn.tp_disenyo.hotel_premier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Servicio;

public interface ServicioDAO extends JpaRepository<Servicio, Long>{
    public Servicio findByTipoServicio(String tipoServicio);
}
