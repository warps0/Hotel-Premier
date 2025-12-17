package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface HabitacionDAO extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    int countByTipoHabitacion(TipoHabitacion tipoHabitacion);
    List<Habitacion> findByCapacidad(Integer capacidad);
    Habitacion findByNumeroHabitacion(Integer numeroHabitacion);
    //List<Habitacion> findAllById(List<Long> ids);
    //List<Habitacion> findByStartDateBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    //¿List<EstadoHabitacion>?
    //List<Habitacion> findByPiso(Piso piso);
}
