package edu.utn.tp_disenyo.hotel_premier.model;

import edu.utn.tp_disenyo.hotel_premier.util.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    private String tipoServicio;
    private Float costoTotal;

    //private String nombre_producto;
    //private int cantidad_producto;
    //private float precio_unitario_producto;
    //private float costo_total;


    //@JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "servicio_id")
    private List<EstadiaServicio> estadias = new ArrayList<>();

    public Servicio(String tipoServicio, Float costoTotal) {
        this.tipoServicio = tipoServicio;
        this.costoTotal = costoTotal;
    }

    @PrePersist
    @PreUpdate
    public void ensureUppercase() {
        if (this.tipoServicio != null) {
            this.tipoServicio = this.tipoServicio.toUpperCase();
        }
    }
}
