package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import io.micrometer.common.lang.NonNull;

public interface ReservaService {
    // TODO: ¿DTO?, Exception
    public Reserva create(@NonNull ReservaCreateDTO reservaDTO) throws Exception;
    public List<Reserva> getAll() throws Exception;
    public Optional<Reserva> getById(Long id) throws Exception;
    public Reserva update(Long id, Reserva reserva) throws Exception;
    public void deleteById(Long id) throws Exception;
}
