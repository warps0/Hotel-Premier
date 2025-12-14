package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import edu.utn.tp_disenyo.hotel_premier.repository.FacturaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.PersonaJuridicaDAO;

import java.util.List;

public class FacturaServiceImpl {

    private final FacturaDAO respository;
    private final PersonaJuridicaDAO pjRepository;
    private final HuespedDAO hRepository;
    private final FacturaService facturaService;
    private final Reserva reservaService;

    public FacturaServiceImpl(FacturaDAO repository, FacturaService facturaService, Reserva reservaService, PersonaJuridicaDAO pjRepository, HuespedDAO hRepository) {
        this.respository = repository;
        this.facturaService = facturaService;
        this.reservaService = reservaService;
        this.pjRepository = pjRepository;
        this.hRepository = hRepository;
    }

    public Factura createFactura(FacturaCreateDTO factura){
        Factura facturaCreada = new Factura(factura);
        return respository.save(facturaCreada);
    }

    public List<Factura> getAll(){
        return facturaService.getAll();
    }

    public Factura getdById(Long id){
        return facturaService.getdById(id);
    }

    public Factura asignarResponsablePago(long idResponsable, long idFactura){
        Factura factura = this.getdById(idFactura);
        Huesped h = hRepository.getReferenceById(idResponsable);
        PersonaJuridica pj = pjRepository.getPersonaJuridicaById(idResponsable);
        if(h==null){
            factura.setResponsable_pago(pj);
        }
        else{
            factura.setResponsable_pago(h);
        }
        return factura;
    }
}
