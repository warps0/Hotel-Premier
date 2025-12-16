package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
import edu.utn.tp_disenyo.hotel_premier.model.Servicio;
import edu.utn.tp_disenyo.hotel_premier.model.Persona;
import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.FacturaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.PersonaJuridicaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaDAO repository;
    private final PersonaJuridicaDAO pjRepository;
    private final HuespedDAO hRepository;
    private final ReservaService reservaService;
    private final EstadiaDAO estadiaRepository;

    public FacturaServiceImpl(FacturaDAO repository, ReservaService reservaService, PersonaJuridicaDAO pjRepository, HuespedDAO hRepository, EstadiaDAO estadiaRepository) {
        this.repository = repository;
        this.reservaService = reservaService;
        this.pjRepository = pjRepository;
        this.hRepository = hRepository;
        this.estadiaRepository = estadiaRepository;
    }

    public Factura createFactura(FacturaCreateDTO factura){
        //TODO: Ojo sino encuentra PersonaJuridica 
        Persona responsableDePago = hRepository.findById(factura.getIdResponsableDePago()).orElseThrow();

        Estadia estadia = estadiaRepository.findById(factura.getIdEstadia()).orElseThrow();

        

        Factura facturaCreada = new Factura(factura, responsableDePago);
        estadia.getFacturas().add(facturaCreada);
        estadiaRepository.save(estadia);

        return repository.save(facturaCreada);
    }

    public List<Factura> getAll(){
        return repository.findAll();
    }

    public Factura getdById(Long id){
        return repository.findById(id).get();
    }

    public Factura asignarResponsablePago(long idResponsable, long idFactura){
        Factura factura = this.getdById(idFactura);
        Huesped h = hRepository.getReferenceById(idResponsable);
        PersonaJuridica pj = pjRepository.getPersonaJuridicaById(idResponsable);
        if(h==null){
            factura.setResponsableDePago(pj);;
        }
        else{
            factura.setResponsableDePago(pj);
        }
        return factura;
    }

    public PersonaJuridica getPersonaJuridicaByRazonSocial(String razonSocial){
        return pjRepository.findByRazonSocial(razonSocial);
    }

    public PersonaJuridica createPersonaJuridica(PersonaJuridica personaJuridica){
        return pjRepository.save(personaJuridica);
    }
}
