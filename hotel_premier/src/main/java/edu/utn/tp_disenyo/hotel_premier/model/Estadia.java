package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estadia")
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Estadia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @ManyToMany
    @JoinTable(
        name = "estadia_huesped",
        joinColumns = @JoinColumn(name = "estadia_id"),
        inverseJoinColumns = @JoinColumn(name = "huesped_id")
    )
    private List<Huesped> huespedes = new ArrayList<>();

    private Long habitacionId;

    @JsonBackReference
    @OneToMany(mappedBy = "estadia")
    private List<Factura> facturas = new ArrayList<>();

    @OneToMany(
        mappedBy = "estadia",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<EstadiaServicio> servicios = new ArrayList<>();

    public void addServicio(Servicio servicio, boolean incluido) {
        EstadiaServicio es = new EstadiaServicio();
        servicio.getEstadias().add(es);
        es.setEstadia(this);
        es.setServicio(servicio);
        es.setIncluido(incluido);
        this.servicios.add(es);
    }

    public void removeServicio(Servicio servicio) {
        servicios.removeIf(es -> es.getServicio().equals(servicio));
    }
}
