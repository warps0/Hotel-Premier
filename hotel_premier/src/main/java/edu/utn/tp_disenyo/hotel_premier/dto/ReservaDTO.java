package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ReservaDTO {
    private Long id;
    private EstadoReserva estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<HuespedDTO> huespedes = new ArrayList<>();
    private String nombre;
    private String apellido;
    private String contacto;
    private List<Long> habitacionesIds = new ArrayList<>();
    private List<EstadiaDTO>  estadias = new ArrayList<>();

    public ReservaDTO(
        Reserva reserva,
        List<Long> habitacionesIds,
        List<HuespedDTO> huespedes
    ) {
        this.id = reserva.getId();
        this.estado = reserva.getEstado();
        this.fechaInicio = reserva.getFechaInicio();
        this.fechaFin = reserva.getFechaFin();
        this.nombre = reserva.getNombre();
        this.apellido = reserva.getApellido();
        this.contacto = reserva.getContacto();

        // IMMUTABLE
        this.habitacionesIds = List.copyOf(habitacionesIds);
        this.huespedes = List.copyOf(huespedes);

        this.estadias = List.copyOf(
                reserva.getEstadias().stream()
                    .map(EstadiaDTO::new)
                    .toList()
        );
    }

    public ReservaDTO(Reserva reserva) {
        this(
            reserva,
            reserva.getHabitaciones().stream().map(Habitacion::getId).toList(),
            reserva.getHuespedes().stream().map(HuespedDTO::new).toList()
        );
    }

}