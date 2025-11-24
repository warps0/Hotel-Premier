package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;

public interface HuespedService {

    // TODO: Manejar excepciones para el método create
    public Huesped create(Huesped huesped) throws HuespedNotSavedException;
    public List<HuespedDTO> getAll(String nombre, String apellido, String documento, TipoDoc tipoDoc);
    public Huesped getById(Long id) throws HuespedNotFoundException;
    public Huesped update(Long id, Huesped huesped) throws HuespedNotFoundException;
    public void deleteById(Long id) throws HuespedNotFoundException;
    public boolean existsByDocumento(String docIdentidad, TipoDoc tipoDoc);
    public Huesped tryToCreate(Huesped huesped) throws HuespedDuplicatedException;

}
