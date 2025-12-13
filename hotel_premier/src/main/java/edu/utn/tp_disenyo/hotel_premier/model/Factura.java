package edu.utn.tp_disenyo.hotel_premier.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "factura")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha_emision;
    private float costo_habitacion;
    private float costo_servicios;
    private char tipo_factura;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFactura;

    public DetalleFactura addDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.add(detalleFactura);
        return detalleFactura;
    }

    public void deleteDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.remove(detalleFactura);
    }
}
