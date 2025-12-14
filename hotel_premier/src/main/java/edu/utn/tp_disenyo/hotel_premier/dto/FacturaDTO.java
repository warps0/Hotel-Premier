package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.DetalleFactura;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaDTO {
    private Long id;
    private LocalDateTime fecha_emision;
    private char tipo_factura;

    private ReservaDTO reserva;

    private List<DetalleFactura> detalleFactura = new ArrayList<>();

    public FacturaDTO(Factura factura) {
        this.id = factura.getId();
        this.fecha_emision = factura.getFecha_emision();
        this.tipo_factura = factura.getTipo_factura();

        this.reserva = new ReservaDTO(factura.getReserva());
        this.detalleFactura = factura.getDetalleFactura();
    }
}