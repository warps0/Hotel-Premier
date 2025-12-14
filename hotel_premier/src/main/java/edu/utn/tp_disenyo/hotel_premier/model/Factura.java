package edu.utn.tp_disenyo.hotel_premier.model;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private LocalDateTime fecha_emision;
    private char tipo_factura = 'a';

    @OneToOne(cascade = CascadeType.ALL)
    private Persona responsable_pago;

    @ManyToOne
    @JoinColumn(name = "reserva")
    private Reserva reserva;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFactura = new ArrayList<>();

    public DetalleFactura addDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.add(detalleFactura);
        return detalleFactura;
    }

    public void deleteDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.remove(detalleFactura);
    }

    public Factura (FacturaCreateDTO facturaCreateDTO){
        this.fecha_emision = LocalDateTime.now();
        if(facturaCreateDTO.getResponsable_pago() instanceof PersonaJuridica){
            this.tipo_factura = 'b';
        }
        this.responsable_pago = null;
        this.responsable_pago = facturaCreateDTO.getResponsable_pago();
        this.detalleFactura = facturaCreateDTO.getDetalleFactura();
    }
}

