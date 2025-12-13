package edu.utn.tp_disenyo.hotel_premier.repository;

import java.time.LocalDateTime;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;

public interface ReservaDAO extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {

    public List<Reserva> findByNombreOrApellidoOrContacto(String nombre, String apellido, String contacto);
    public List<Reserva> findByEstado(EstadoReserva estado) throws Exception;
    public List<Reserva> findByFechaFin(LocalDateTime fechaFin) throws Exception;
}
