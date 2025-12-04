package edu.utn.tp_disenyo.hotel_premier.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.HabitacionNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.util.Piso;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;

public interface HabitacionService {
    //TODO: Excepciones habitación
    public String initHabitaciones() throws Exception;
    public Habitacion create(TipoHabitacion tipo) throws Exception;
    public List<Habitacion> getAll();
    public Optional<Habitacion> getById(Long id) throws Exception;
    public void deleteById(Long id) throws Exception;
    public void updateById(Long id, Habitacion habitacion) throws Exception;
    public Habitacion agregarEstado(Long idHabitacion, EstadoHabitacion estadoHabitacion) throws Exception;
    public Habitacion borrarEstado(Long idHabitacion, EstadoHabitacion estadoHabitacion) throws Exception;
    public List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    //public List<Habitacion> findByPiso(Piso piso);
    public List<Habitacion> findByCapacidad(Integer capacidad);

    public List<HabitacionDTO> getHabitacionesByRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    public HabitacionDTO getHabitacionByRangoFecha(Long idHabitacion, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws Exception;

    
}
