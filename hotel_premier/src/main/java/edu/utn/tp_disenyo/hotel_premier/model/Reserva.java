package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private EstadoReserva estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Huesped responsable;

    
    @ManyToMany
    //@JsonManagedReference
    @JoinTable(
        name = "reserva_habitacion",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "habitacion_id")
    )
    private List<Habitacion> habitaciones = new ArrayList<>();


    public Reserva(EstadoReserva estado, LocalDateTime fechaInicio, LocalDateTime fechaFin, Huesped responsable, List<Habitacion> habitaciones) {
        this.fechaCreacion = LocalDateTime.now();
        
        this.estado = estado;
        this.responsable = responsable;

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.habitaciones = habitaciones;
    }

    /*
    public void addHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
        habitacion.getReservas().add(this);
    }

    public void removeHabitacion(Habitacion habitacion) {
        habitaciones.remove(habitacion);
        habitacion.getReservas().remove(this);
    }
    */
}
