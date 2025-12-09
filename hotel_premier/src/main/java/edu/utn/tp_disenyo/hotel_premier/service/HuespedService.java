package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;

public interface HuespedService {
    // TODO: Manejar excepciones para el método create
    public Huesped create(Huesped huesped) throws EntityNotSavedException;
    public List<HuespedDTO> getAll(String nombre, String apellido, String documento, TipoDoc tipoDoc);
    public Huesped getById(Long id) throws EntityNotSavedException, HuespedNotFoundException;
    public Huesped update(Long id, Huesped huesped) throws HuespedNotFoundException;
    public void deleteById(Long id) throws EntityNotSavedException;
    public boolean existsByDocumento(String docIdentidad, TipoDoc tipoDoc);
    public Huesped tryToCreate(Huesped huesped) throws HuespedDuplicatedException;
    public List<Huesped> findAllByIds(List<Long> ids) throws Exception;
    public boolean huespedReservado(long huespedId) throws Exception;
}
