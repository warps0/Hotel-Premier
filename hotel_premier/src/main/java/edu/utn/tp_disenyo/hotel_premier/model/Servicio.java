package edu.utn.tp_disenyo.hotel_premier.model;

import edu.utn.tp_disenyo.hotel_premier.util.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo_servicio;
    private Float costo_total;

    //private String nombre_producto;
    //private int cantidad_producto;
    //private float precio_unitario_producto;
    //private float costo_total;

    @OneToMany(mappedBy = "servicio")
    private List<EstadiaServicio> estadias;

    public Servicio(String tipoServicio, Float costoTotal) {
        this.tipo_servicio = tipoServicio;
        this.costo_total = costoTotal;
    }
}
