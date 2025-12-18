package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
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

    @ManyToOne
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

    //@JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "estadia_id")
    private List<EstadiaServicio> servicios = new ArrayList<>();

    public void addServicio(Servicio servicio, boolean incluido) {
        boolean exists = servicios.stream()
                .anyMatch(es -> es.getServicioId() != null && es.getServicioId().equals(servicio.getId()));

        if (exists) return;

        EstadiaServicio es = new EstadiaServicio();

        es.setEstadiaId(this.id);
        es.setServicioId(servicio.getId());
        es.setIncluido(incluido);

        servicios.add(es);
    }

    public void removeServicio(Servicio servicio) {
        servicios.removeIf(es -> es.getServicioId() != null && es.getServicioId().equals(servicio.getId()));
    }
}
