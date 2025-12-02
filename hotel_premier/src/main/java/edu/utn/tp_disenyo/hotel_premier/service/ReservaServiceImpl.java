package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.repository.ReservaDAO;
import io.micrometer.common.lang.NonNull;

@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaDAO reservaRepository;
    private final HuespedService huespedService;
    private final HabitacionService habitacionService;

    public ReservaServiceImpl(ReservaDAO rRep, HuespedService hServ, HabitacionService habServ) {
        this.reservaRepository = rRep;
        this.huespedService = hServ;
        this.habitacionService = habServ;
    }

    @Override
    public Reserva create(@NonNull ReservaCreateDTO reservaDTO) throws Exception {
        Huesped responsable = huespedService.getById(reservaDTO.getResponsableId());
        List<Habitacion> habitaciones = new ArrayList<>();
        for(Long habitacionId: reservaDTO.getHabitacionesIds()){
            habitaciones.add(habitacionService.getById(habitacionId).get());
        }
        //List<Habitacion> habitaciones = habitacionService.findAllById(reservaDTO.getHabitacionesIds());

        Reserva reserva = new Reserva(
            reservaDTO.getEstado(),
            reservaDTO.getFechaInicio(),
            reservaDTO.getFechaFin(),
            responsable
        );

        //habitaciones.forEach(reserva::addHabitacion);

        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> getAll() throws Exception {
        return Optional.ofNullable(reservaRepository.findAll()).orElseThrow(() -> new Exception());
    }

    @Override
    public Optional<Reserva> getById(Long id) throws Exception {
        return Optional.ofNullable(reservaRepository.findById(id)).orElseThrow(
                () -> new Exception() //ReservaNotFoundException()
        );
    }

    @Override
    public Reserva update(Long id, Reserva reserva) throws Exception {
        Reserva reservaActualizada = reservaRepository.findById(id).orElseThrow( () -> new Exception()); //ReservaNotFoundException()

        reservaActualizada.setEstado(reserva.getEstado());
        reservaActualizada.setHabitaciones(reserva.getHabitaciones());
        reservaActualizada.setFechaInicio(reserva.getFechaInicio());
        reservaActualizada.setFechaFin(reserva.getFechaFin());
        reservaActualizada.setFechaCreacion(reserva.getFechaCreacion());
        reservaActualizada.setResponsable(reserva.getResponsable());

        return reservaRepository.save(reservaActualizada);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Reserva reservaBorrada = reservaRepository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()
        reservaRepository.delete(reservaBorrada);
    }

}
