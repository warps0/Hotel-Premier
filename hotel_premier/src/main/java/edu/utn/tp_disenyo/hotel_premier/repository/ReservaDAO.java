package edu.utn.tp_disenyo.hotel_premier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long> {

    public List<Reserva> findByNombreOrApellidoOrContacto(String nombre, String apellido, String contacto);
    
}
