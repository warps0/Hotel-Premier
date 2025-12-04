package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;

import java.time.LocalDateTime;
import java.util.List;

public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    //List<Habitacion> findByPiso(Piso piso);
    List<Habitacion> findByCapacidad(Integer capacidad);
    int countByTipoHabitacion(TipoHabitacion tipoHabitacion);
    //¿List<EstadoHabitacion>?
    //List<Habitacion> findByStartDateBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    // List<Habitacion> findAllById(List<Long> ids);
}
