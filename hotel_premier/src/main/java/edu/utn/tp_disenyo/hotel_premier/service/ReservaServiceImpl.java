package edu.utn.tp_disenyo.hotel_premier.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.repository.HabitacionDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.ReservaDAO;
import io.micrometer.common.lang.NonNull;

@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaDAO reservaRepository;
    private final HuespedDAO huespedRepository;
    private final HabitacionDAO habitacionRepository;

    public ReservaServiceImpl(ReservaDAO rRep, HuespedDAO hRep, HabitacionDAO habRep) {
        this.reservaRepository = rRep;
        this.huespedRepository = hRep;
        this.habitacionRepository = habRep;
    }

    @Override
    public Reserva create(@NonNull ReservaCreateDTO reservaDTO) throws Exception {
        Huesped responsable = huespedRepository.findById(reservaDTO.getResponsableId())
        .orElseThrow(() -> new RuntimeException("Huesped no encontrado"));

        List<Habitacion> habitaciones = habitacionRepository.findAllById(reservaDTO.getHabitacionesIds());

        Reserva reserva = new Reserva(
            reservaDTO.getEstado(),
            reservaDTO.getFechaInicio(),
            reservaDTO.getFechaFin(),
            responsable
        );

        habitaciones.forEach(reserva::addHabitacion);

        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> getAll() throws Exception {
        return Optional.ofNullable(reservaRepository.findAll()).orElseThrow(() -> new Exception());
    }

    @Override
    public Reserva getById(Long id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Reserva update(Long id, Reserva reserva) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Long id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
