package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    
    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "reserva_huesped",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "huesped_id")
    )
    private List<Huesped> huespedes = new ArrayList<>();

    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "reserva_habitacion",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "habitacion_id")
    ) 
    private List<Habitacion> habitaciones = new ArrayList<>();

    // Al momento de crear una reserva, la lista de estadías será vacía
    // Para asociar estas, se usaran métodos internos a la clase
    @JsonBackReference
    @OneToMany(
        mappedBy = "reserva",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Estadia> estadias = new ArrayList<>();

    

    public Reserva(EstadoReserva estado, ReservaCreateDTO dto, List<Habitacion> habitaciones, List<Huesped> huespedes) {
        this.fechaCreacion = LocalDateTime.now();
        
        this.estado = estado;

        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.contacto = dto.getContacto();

        this.fechaInicio = dto.getFechaInicio();
        this.fechaFin = dto.getFechaFin();
        this.habitaciones = habitaciones;
        this.huespedes = huespedes;
    }

    @PrePersist
    @PreUpdate
    public void ensureUppercase() {
        if (this.nombre != null) {
            this.nombre = this.nombre.toUpperCase();
        }
        if (this.apellido != null) {
            this.apellido = this.apellido.toUpperCase();
        }
        if (this.contacto != null) {
            this.contacto = this.contacto.toUpperCase();
        }
    }
}
