package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.repository.HabitacionDAO;
import edu.utn.tp_disenyo.hotel_premier.util.Piso;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionServiceImpl implements HabitacionService{

    private final HabitacionDAO repository;

    public HabitacionServiceImpl(HabitacionDAO repository) {
        this.repository = repository;
    }


    @Override
    public Habitacion create(@NonNull Habitacion habitacion) throws Exception {
        return Optional.ofNullable(repository.save(habitacion)).orElseThrow(
                () -> new Exception() //HabitacionNotFoundException()
        );
    }

    @Override
    public List<Habitacion> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Habitacion> getById(Long id) throws Exception {
        return Optional.ofNullable(repository.findById(id)).orElseThrow(
                () -> new Exception() //HabitacionNotFoundException()
        );
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Habitacion habitacionBorrada = repository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()
        repository.delete(habitacionBorrada);
    }

    @Override
    public void updateById(Long id, Habitacion habitacion) throws Exception {
        Habitacion habitacionActualizada = repository.findById(id).orElseThrow( () -> new Exception()); //HabitacionNotFoundException()

        habitacionActualizada.setCapacidad(habitacion.getCapacidad());
        habitacionActualizada.setPrecio(habitacion.getPrecio());
        habitacionActualizada.setTipoHabitacion(habitacion.getTipoHabitacion());
        habitacionActualizada.setPiso(habitacion.getPiso());
        habitacionActualizada.setHistorialEstado(habitacion.getHistorialEstado());

    }

    @Override
    public List<Habitacion> findByTipoHabitacion(TipoHabitacion tipoHabitacion) {
        return repository.findByTipoHabitacion(tipoHabitacion);
    }

    @Override
    public List<Habitacion> findByPiso(Piso piso) {
        return repository.findByPiso(piso);
    }

    @Override
    public List<Habitacion> findByCapacidad(Integer capacidad) {
        return repository.findByCapacidad(capacidad);
    }

    public List<HabitacionDTO> getHabitacionesByRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Habitacion> habitaciones = this.getAll();
        List<HabitacionDTO> listaDTO = new ArrayList<>();

        for (Habitacion habitacion : habitaciones) {
            HabitacionDTO habitacionDTO = new HabitacionDTO(habitacion.getId(), habitacion.getTipoHabitacion(), habitacion.getHistorialEstado());
            listaDTO.add(habitacionDTO);
        }

        for(HabitacionDTO habitacion : listaDTO) {
            //TODO: Filtrar historialEstado en base a fechaInicio y fechaFin
            // Busco aproximación por izq mas cercana fechaInicio
            // Busco aproximación por der mas cercana fechaFin
            int fromIdex = -1;
            int toIndex = -1;
            
            for(EstadoHabitacion e: habitacion.getHistorialEstado()){
                if(e.getFechaInicio().isBefore(fechaInicio)){
                    fromIdex = habitacion.getHistorialEstado().indexOf(e);
                }
                else if(e.getFechaFin().isAfter(fechaFin)){
                    toIndex = habitacion.getHistorialEstado().indexOf(e);
                }
            }
            // JWT SECRET KEY
            if(fromIdex != -1 && toIndex != -1){
                List<EstadoHabitacion> subList = habitacion.getHistorialEstado().subList(fromIdex, toIndex);
                habitacion.setHistorialEstado(subList);
            }
        }

        return listaDTO;
    }
}
