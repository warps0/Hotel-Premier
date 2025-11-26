package edu.utn.tp_disenyo.hotel_premier.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.util.Piso;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;

public interface HabitacionService {
    //TODO: Excepciones habitación
    public Habitacion create(Habitacion habitacion) throws Exception;
    public List<Habitacion> getAll();
    public Optional<Habitacion> getById(Long id) throws Exception;
    public void deleteById(Long id) throws Exception;
    public void updateById(Long id, Habitacion habitacion) throws Exception;
    public List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion);
    public List<Habitacion> findByPiso(Piso piso);
    public List<Habitacion> findByCapacidad(Integer capacidad);

    public List<HabitacionDTO> getHabitacionesByRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
