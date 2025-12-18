package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "habitacion")
public class Habitacion {
    //¿Implementar patrón factory?

    public static int cont_ind_estandar = 100;
    public static int cont_doble_estandar = 200;
    public static int cont_doble_superior = 300;
    public static int cont_superior_family = 400;
    public static int cont_suite = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacidad;
    private Float precio;
    private TipoHabitacion tipoHabitacion;
    // private Piso piso; erase una vez...
    private int numeroHabitacion;
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<EstadoHabitacion> historialEstado = new ArrayList<>();

    @ManyToMany(mappedBy = "habitaciones")
    private List<Reserva> reservas = new ArrayList<>();

    public void addEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        this.historialEstado.add(estadoHabitacion);
    }

    public EstadoHabitacion getEstadoHabitacion(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        EstadoHabitacion temp = null;

        for(EstadoHabitacion e : historialEstado){
            if(e.getFechaInicio().isEqual(fechaInicio) && e.getFechaFin().isEqual(fechaFin)){
                temp = e;
                break;
            }
        }

        return temp;
    }

    public void removeEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        this.historialEstado.remove(estadoHabitacion);
    }
}
