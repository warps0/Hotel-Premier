package edu.utn.tp_disenyo.hotel_premier.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.util.Pair;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    //List<Servicios>
}
