package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;

import java.util.List;

public interface HuespedService {

    // TODO: Manejar excepciones para el método create
    public Huesped create(Huesped huesped) throws Exception;
    public List<Huesped> getAll();
    public Huesped getById(Long id) throws HuespedNotFoundException;
    public Huesped update(Long id, Huesped huesped) throws HuespedNotFoundException;
    public void deleteById(Long id) throws HuespedNotFoundException;

}
