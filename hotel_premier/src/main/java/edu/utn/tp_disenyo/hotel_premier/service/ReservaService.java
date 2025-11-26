package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;

public interface ReservaService {
    // TODO: ¿DTO?, Exception
    public Reserva create() throws Exception;
    public List<Reserva> getAll() throws Exception;
    public Reserva getById(Long id) throws Exception;
    public Reserva update(Long id, Reserva reserva) throws Exception;
    public void deleteById(Long id) throws Exception;
}
