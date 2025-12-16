package edu.utn.tp_disenyo.hotel_premier.dto;

import edu.utn.tp_disenyo.hotel_premier.model.DetalleFactura;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import edu.utn.tp_disenyo.hotel_premier.model.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FacturaCreateDTO {
    //private char tipo_factura;
    //private Persona responsable_pago = null;
    private Long idResponsableDePago;
    private Long idEstadia;
    private List<EstadiaServicio> servicios;

    //TODO: Factura debería manejarlo
    // private List<DetalleFactura> detalleFactura = new ArrayList<>();
}
