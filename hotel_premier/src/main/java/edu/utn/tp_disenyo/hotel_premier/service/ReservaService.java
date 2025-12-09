package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import io.micrometer.common.lang.NonNull;

public interface ReservaService {
    // TODO: ¿DTO?, Exception
    public ReservaDTO create(@NonNull ReservaCreateDTO reservaDTO) throws Exception;
    public List<ReservaDTO> getAll() throws Exception;
    public Optional<Reserva> getById(Long id) throws Exception;
    public Reserva update(Long id, Reserva reserva) throws Exception;
    public void deleteById(Long id) throws Exception;
    public ReservaDTO agregarHuesped(Long id, List<HuespedDTO> huespedes) throws Exception;
    public List<ReservaDTO> getByResponsable(String nombre, String apellido, String contacto) throws Exception;

    public EstadiaDTO ocuparHabitacion(Long reservaId, Long habitacionId, List<Long> huespedesId) throws Exception;

    public void cancelarReserva(List<Long> reservaIds) throws Exception;
}
