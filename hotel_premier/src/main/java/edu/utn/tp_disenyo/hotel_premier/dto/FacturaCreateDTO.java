package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.DetalleFactura;
import edu.utn.tp_disenyo.hotel_premier.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FacturaCreateDTO {
    private char tipo_factura;
    private Persona responsable_pago = null;
    private ReservaDTO reserva;
    private List<DetalleFactura> detalleFactura = new ArrayList<>();
}
