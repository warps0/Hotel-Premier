package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;

public interface HabitacionService {
    //TODO: Excepciones habitación
    public Habitacion create(Habitacion habitacion);
    public List<Habitacion> getAll();
    public Habitacion getById(Long id);
    public void deleteById(Long id);
}
