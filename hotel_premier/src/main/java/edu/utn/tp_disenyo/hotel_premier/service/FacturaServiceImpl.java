package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
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

    public FacturaServiceImpl(FacturaDAO repository, ReservaService reservaService, PersonaJuridicaDAO pjRepository, HuespedDAO hRepository) {
        this.repository = repository;
        this.reservaService = reservaService;
        this.pjRepository = pjRepository;
        this.hRepository = hRepository;
    }

    public Factura createFactura(FacturaCreateDTO factura){
        Factura facturaCreada = new Factura(factura);
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
            factura.setResponsable_pago(pj);
        }
        else{
            factura.setResponsable_pago(h);
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
