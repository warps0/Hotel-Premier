package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    // Reserva a nombre de:
    private String nombre;
    private String apellido;
    private String contacto;
    
    /* 
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Huesped responsable; 
    */
    @ManyToMany
    @JoinTable(
        name = "reserva_huesped",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "huesped_id")
    )
    private List<Huesped> huespedes = new ArrayList<>();

    
    @ManyToMany
    //@JsonIgnore
    @JoinTable(
        name = "reserva_habitacion",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "habitacion_id")
    ) 
    private List<Habitacion> habitaciones = new ArrayList<>();


    public Reserva(EstadoReserva estado, ReservaCreateDTO dto, List<Habitacion> habitaciones, List<Huesped> huespedes) {
        this.fechaCreacion = LocalDateTime.now();
        
        this.estado = estado;
        //this.responsable = responsable;

        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.contacto = dto.getContacto();

        this.fechaInicio = dto.getFechaInicio();
        this.fechaFin = dto.getFechaFin();
        this.habitaciones = habitaciones;
        this.huespedes = huespedes;
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
