package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
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
/* 
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
            else throw new Exception("");
        }        

        // LOGICA DE SI ES POSIBLE CREAR LA RESERVA:

        Reserva reserva = new Reserva(
            EstadoReserva.ACTIVA, // 1
            reservaDTO,
            habitaciones
        );

        reservaRepository.save(reserva);

        return new ReservaDTO(reserva, reservaDTO.getHabitacionesIds());
    }
 */
    @Override
    public ReservaDTO create(@NonNull ReservaCreateDTO reservaDTO) throws Exception {

        // ============================
        // 1. Fetch and validate rooms
        // ============================
        List<Habitacion> habitaciones = new ArrayList<>();

        for (Long habitacionId : reservaDTO.getHabitacionesIds()) {

            HabitacionDTO h = habitacionService.getHabitacionByRangoFecha(
                    habitacionId, reservaDTO.getFechaInicio(), reservaDTO.getFechaFin()
            );

            // Room MUST be free
            if (!h.getHistorialEstado().isEmpty()) {
                throw new Exception("Habitación " + habitacionId + " no está disponible en el rango indicado");
            }

            Habitacion hab = habitacionService.getById(habitacionId)
                .orElseThrow(() -> new Exception("Habitación no encontrada"));

            habitaciones.add(hab);

            // Add reservation status to the room
            EstadoHabitacion e = new EstadoHabitacion(
                    reservaDTO.getFechaInicio(),
                    reservaDTO.getFechaFin(),
                    Estado.RESERVADO
            );

            habitacionService.agregarEstado(habitacionId, e);
        }

        // ============================
        // 2. Fetch guests (optional)
        // ============================
        List<Huesped> huespedes = reservaDTO.getHuespedesIds() == null || reservaDTO.getHuespedesIds().isEmpty() ? 
            new ArrayList<>()
            : huespedService.findAllByIds(reservaDTO.getHuespedesIds());

        List<HuespedDTO> huespedesDTO = new ArrayList<>();

        for(Huesped h : huespedes) {
            HuespedDTO dto = new HuespedDTO(h);
            huespedesDTO.add(dto);
        }

        // ============================
        // 3. Create and save reservation
        // ============================
        Reserva reserva = new Reserva(
                EstadoReserva.ACTIVA,
                reservaDTO,
                habitaciones,
                huespedes
        );

        reservaRepository.save(reserva);

        return new ReservaDTO(reserva, reservaDTO.getHabitacionesIds(), huespedesDTO);
    }

    @Override
    public List<ReservaDTO> getAll() throws Exception {
        List<Reserva> reservas = Optional.ofNullable(reservaRepository.findAll()).orElseThrow(() -> new Exception());
        List<ReservaDTO> result = new ArrayList<>();

        List<HuespedDTO> huespedes = new ArrayList<>();

        for(Reserva reserva : reservas){
            List<Long> habitacionesIds = new ArrayList<>();

            for(Habitacion habitacionReservada : reserva.getHabitaciones()){
                habitacionesIds.add(habitacionReservada.getId());
            }

            for(Huesped h : reserva.getHuespedes()) {
                HuespedDTO dto = new HuespedDTO(h);
                huespedes.add(dto);
            }
            result.add(new ReservaDTO(reserva, habitacionesIds, huespedes));
        }

        return result;
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

        return reservaRepository.save(reservaActualizada);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Reserva reservaBorrada = reservaRepository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()
        reservaRepository.delete(reservaBorrada);
    }

    @Override
    public ReservaDTO agregarHuesped(Long id, List<HuespedDTO> huespedes) throws Exception {
        List<Long> ids = huespedes.stream()
            .map(HuespedDTO::getId)
            .toList();

        List<Huesped> lista = huespedService.findAllByIds(ids);
        Reserva actualizada = reservaRepository.findById(id).get();
        actualizada.setHuespedes(lista);

        reservaRepository.save(actualizada);

        return new ReservaDTO(actualizada, ids, huespedes);
    }

    @Override
    public List<ReservaDTO> getByResponsable(String nombre, String apellido, String contacto) throws Exception {
        List<Reserva> reservas = reservaRepository.findByNombreOrApellidoOrContacto(nombre, apellido, contacto);
        List<ReservaDTO> reservasDTO = new ArrayList<>();


        List<Long> idsHabitaciones = reservas.stream()
            .map(Reserva::getId)
            .toList();

        for(Reserva r: reservas){
            List<HuespedDTO> huespedes = r.getHuespedes().stream()
                .map(h -> new HuespedDTO(h))
                .toList();
            ReservaDTO dto = new ReservaDTO(r, idsHabitaciones, huespedes);
            reservasDTO.add(dto);
        }

        return reservasDTO;
    }

}
