package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HabitacionDAO;
import edu.utn.tp_disenyo.hotel_premier.util.Estado;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion.*;

@Service
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionDAO habitacionRepository;
    private final EstadiaDAO estadiaRepository;
    private final HuespedService huespedService;

    public HabitacionServiceImpl(HabitacionDAO repository, EstadiaDAO estadiaRepository, HuespedService huespedService) {
        this.habitacionRepository = repository;
        this.estadiaRepository = estadiaRepository;
        this.huespedService = huespedService;
    }

    @Override
    public String initHabitaciones() throws Exception {
        // TODO: Inicializar instancias con Spring inicializer
        for (int i = 0; i < 10; i++) {
            this.create(INDIVIDUAL_ESTANDAR);
        }
        for (int i = 0; i < 18; i++) {
            this.create(DOBLE_ESTANDAR);
        }
        for (int i = 0; i < 8; i++) {
            this.create(DOBLE_SUPERIOR);
        }
        for (int i = 0; i < 10; i++) {
            this.create(SUPERIOR_FAMILY_PLAN);
        }

        for (int i = 0; i < 2; i++) {
            this.create(SUITE_DOBLE);
        }

        return "god no?";
    }

    // TODO: HabitacionCreateDTO
    @Override
    public Habitacion create(TipoHabitacion tipo) throws Exception {
        Habitacion habitacion = new Habitacion();
        habitacion.setTipoHabitacion(tipo);

        int cantHabitacionesDeTipo = habitacionRepository.countByTipoHabitacion(tipo);
        cantHabitacionesDeTipo++; // Incremento en uno, creo la primera habitación

        // Forzar listas de estados y reservas vacías
        habitacion.setHistorialEstado(new ArrayList<EstadoHabitacion>());
        habitacion.setReservas(new ArrayList<Reserva>());

        // Precio, capacidad, numero POR TIPO de habitación
        // Atado con alambre, se debería hacer un factory ;)
        switch(tipo) {
            case INDIVIDUAL_ESTANDAR:
                habitacion.setPrecio(50800F);
                habitacion.setCapacidad(1);
                habitacion.setNumeroHabitacion(Habitacion.cont_ind_estandar + cantHabitacionesDeTipo);
                break;
            case DOBLE_ESTANDAR:
                habitacion.setPrecio(70230F);
                habitacion.setCapacidad(2);
                habitacion.setNumeroHabitacion(Habitacion.cont_doble_estandar + cantHabitacionesDeTipo);
                break;
            case DOBLE_SUPERIOR:
                habitacion.setPrecio(90560F);
                habitacion.setCapacidad(2);
                habitacion.setNumeroHabitacion(Habitacion.cont_doble_superior + cantHabitacionesDeTipo);
                break;
            case SUPERIOR_FAMILY_PLAN:
                habitacion.setPrecio(110500F);
                habitacion.setCapacidad(5);
                habitacion.setNumeroHabitacion(Habitacion.cont_superior_family + cantHabitacionesDeTipo);
                break;
            case SUITE_DOBLE:
                habitacion.setPrecio(128600F);
                habitacion.setCapacidad(2);
                habitacion.setNumeroHabitacion(Habitacion.cont_suite + cantHabitacionesDeTipo);
                break;
            default:
                throw new Exception();
        }

        return Optional.ofNullable(habitacionRepository.save(habitacion)).orElseThrow(
                () -> new Exception() // EntityNotSavedException()
        );
    }

    @Override
    public List<Habitacion> getAll() {
        return habitacionRepository.findAll();
    }

    @Override
    public Optional<Habitacion> getById(Long id) throws Exception {
        return Optional.ofNullable(habitacionRepository.findById(id)).orElseThrow(
                () -> new Exception() //HabitacionNotFoundException()
        );
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Habitacion habitacionBorrada = habitacionRepository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()
        habitacionRepository.delete(habitacionBorrada);
    }

    @Override
    public void updateById(Long id, Habitacion habitacion) throws Exception {
        // TODO: PATCH acá forzamos que si llega null se pone null
        Habitacion habitacionActualizada = habitacionRepository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()

        habitacionActualizada.setCapacidad(habitacion.getCapacidad());
        habitacionActualizada.setPrecio(habitacion.getPrecio());
        habitacionActualizada.setTipoHabitacion(habitacion.getTipoHabitacion());
        // habitacionActualizada.setPiso(habitacion.getPiso());
        habitacionActualizada.setHistorialEstado(habitacion.getHistorialEstado());

    }

    @Override
    public Habitacion agregarEstado(Long idHabitacion, EstadoHabitacion estadoHabitacion) throws Exception {
        Habitacion habitacion = this.getById(idHabitacion).get();

        habitacion.addEstadoHabitacion(estadoHabitacion);
        estadoHabitacion.setHabitacion(idHabitacion);

        return habitacionRepository.save(habitacion);
    }

    @Override
    public Habitacion borrarEstado(Long idHabitacion, EstadoHabitacion estadoHabitacion) throws Exception {
        Habitacion habitacion = this.getById(idHabitacion).get();

        habitacion.removeEstadoHabitacion(estadoHabitacion);
        return habitacionRepository.save(habitacion);
    }

    @Override
    public List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion) {
        return habitacionRepository.findByTipoHabitacion(tipoHabitacion);
    }

//    @Override
//    public List<Habitacion> findByPiso(Piso piso) {
//        return repository.findByPiso(piso);
//    }

    @Override
    public List<Habitacion> findByCapacidad(Integer capacidad) {
        return habitacionRepository.findByCapacidad(capacidad);
    }

    @Override
    public List<HabitacionDTO> getHabitacionesByRangoFecha(LocalDateTime inputInicio, LocalDateTime inputFin) {
        return this.getAll()
        .stream()
        .map(h -> {
            List<EstadoHabitacion> filtrados = h.getHistorialEstado()
                .stream()
                .filter(e ->
                        !e.getFechaFin().isBefore(inputInicio) &&
                        !e.getFechaInicio().isAfter(inputFin)
                )
                .toList();

            return new HabitacionDTO(
                    h.getId(),
                    h.getTipoHabitacion(),
                    filtrados
            );
        })
        .toList();
    }

    @Override
    public HabitacionDTO getHabitacionByRangoFecha(Long idHabitacion, LocalDateTime inputInicio, LocalDateTime inputFin) throws Exception {

        List<EstadoHabitacion> filtrados = this.getById(idHabitacion).get().getHistorialEstado()
            .stream()
            .filter(e ->
                !e.getFechaFin().isBefore(inputInicio) &&
                !e.getFechaInicio().isAfter(inputFin)
            )
            .toList();
        
        return new HabitacionDTO(
            idHabitacion,
            this.getById(idHabitacion)
                .get()
                .getTipoHabitacion(), // ?
            filtrados
        );
    }

    @Override
    public Habitacion getByNumeroHabitacion(Integer numeroHabitacion){
        return habitacionRepository.findByNumeroHabitacion(numeroHabitacion);
    }

}
