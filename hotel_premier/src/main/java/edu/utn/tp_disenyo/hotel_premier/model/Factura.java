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
    private char tipo_factura = 'A';

    @OneToOne(cascade = CascadeType.ALL)
    private Persona responsableDePago;

    @ManyToOne
    @JoinColumn(name = "estadia_id")
    private Estadia estadia;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFactura = new ArrayList<>();

    public Factura(FacturaCreateDTO facturaDTO, Persona responsableDePago) {
    }

    public DetalleFactura addDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.add(detalleFactura);
        return detalleFactura;
    }

    public void deleteDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.remove(detalleFactura);
    }

    public Factura (FacturaCreateDTO facturaCreateDTO, Persona responsable, Estadia estadia){
        this.fecha_emision = LocalDateTime.now();
        if(responsable instanceof PersonaJuridica){
            this.tipo_factura = 'B';
        }
        this.responsableDePago = responsable;

        this.estadia = estadia;
    }
}

