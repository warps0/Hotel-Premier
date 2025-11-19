package edu.utn.tp_disenyo.hotel_premier.model;

import java.util.ArrayList;
import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.util.Piso;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer capacidad;
    private Float precio;
    private TipoHabitacion tipoHabitacion;
    private Piso piso;
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstadoHabitacion> historialEstado = new ArrayList<>();
}
