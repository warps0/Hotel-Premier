package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.repository.ReservaDAO;
import edu.utn.tp_disenyo.hotel_premier.util.Estado;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
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

/* 
    1. Hacer un getByRangoFecha con fechaInicio y fechaFin de ReservaCreateDTO (lista #1)
    2. Obtener una lista de las habitaciones sabiendo el id (lista #2).
    3. De la lista #1, me quedo solamente con las habitaciones cuyo id está en la lista #2.
    4. Para que la reserva se pueda crear, TODAS las habitaciones de lista #3 deben tener la lista de EstadoHabitacion VACÍO.
 */

    @Override
    public ReservaDTO create(@NonNull ReservaCreateDTO reservaDTO) throws Exception {
        // Se buscan las instancias de habitaciones que pertenecen a la reserva para asociarlas
        List<Habitacion> habitaciones = new ArrayList<>();
        for(Long habitacionId: reservaDTO.getHabitacionesIds()){

            HabitacionDTO h = habitacionService.getHabitacionByRangoFecha(habitacionId, reservaDTO.getFechaInicio(), reservaDTO.getFechaFin());

            if(h.getHistorialEstado().isEmpty()) {
                Habitacion hab = habitacionService.getById(habitacionId).get();
                habitaciones.add(hab);
                EstadoHabitacion e = new EstadoHabitacion(reservaDTO.getFechaInicio(), reservaDTO.getFechaFin(), Estado.RESERVADO);
                habitacionService.agregarEstado(habitacionId, e);
            }
            // TODO: Che esto ojo ;)
            else throw new Exception();

        }        

        // LOGICA DE SI ES POSIBLE CREAR LA RESERVA:

        Huesped huesped = huespedService.getById(1L);
        Reserva reserva = new Reserva(
            EstadoReserva.ACTIVA,
            reservaDTO.getFechaInicio(),
            reservaDTO.getFechaFin(),
            huesped, // Responsable de la reserva se modifica manualmente y aparte
            habitaciones
        );

        reservaRepository.save(reserva);

        return new ReservaDTO(reserva, reservaDTO.getHabitacionesIds());
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
