package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;

import java.util.List;

public interface FacturaService {
    public Factura createFactura(FacturaCreateDTO factura);
    //public Factura updateFactura(Factura factura);
    //public void deleteFactura(Factura factura);
    public List<Factura> getAll();
    public Factura getdById(Long id);
    public Factura asignarResponsablePago(long idResponsable, long idFactura);
    public PersonaJuridica getPersonaJuridicaByRazonSocial(String razonSocial);
    public PersonaJuridica createPersonaJuridica(PersonaJuridica personaJuridica);
    public EstadiaServicio pagarServicio(Long idEstadiaServicio);
}
