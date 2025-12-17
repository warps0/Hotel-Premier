package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.FacturaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.*;
import edu.utn.tp_disenyo.hotel_premier.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaDAO repository;
    private final PersonaJuridicaDAO pjRepository;
    private final HuespedDAO hRepository;
    private final EstadiaDAO estadiaRepository;
    private final EstadiaServicioDAO estadiaServicioRepository;

    public FacturaServiceImpl(FacturaDAO repository,
                              PersonaJuridicaDAO pjRepository,
                              HuespedDAO hRepository,
                              EstadiaDAO estadiaRepository,
                              EstadiaServicioDAO estadiaServicioRepository) {
        this.repository = repository;
        this.pjRepository = pjRepository;
        this.hRepository = hRepository;
        this.estadiaRepository = estadiaRepository;
        this.estadiaServicioRepository = estadiaServicioRepository;
    }

    @Override
    public Factura createFactura(FacturaCreateDTO facturaDTO){
        //TODO: Ojo sino encuentra PersonaJuridica
        Persona responsableDePago = hRepository.findById(facturaDTO.getIdResponsableDePago()).orElseThrow();

        Estadia estadia = estadiaRepository.findById(facturaDTO.getIdEstadia()).orElseThrow();

        Factura facturaCreada = new Factura(facturaDTO, responsableDePago, estadia);

        estadia.getFacturas().add(facturaCreada);

        if (facturaDTO.getServicios() != null) {
            for (EstadiaServicio item : facturaDTO.getServicios()) {
                item.setEstadiaId(facturaDTO.getIdEstadia());
                estadiaServicioRepository.save(item);
            }
        }

        estadiaRepository.save(estadia);

        return repository.save(facturaCreada);
    }

    @Override
    public List<Factura> getAll(){
        return repository.findAll();
    }

    @Override
    public Factura getdById(Long id){
        return repository.findById(id).get();
    }

    @Override
    public Factura asignarResponsablePago(long idResponsable, long idFactura){
        Factura factura = this.getdById(idFactura);
        Huesped h = hRepository.findById(idResponsable).orElse(null);

        if(h == null){
            PersonaJuridica pj = pjRepository.findById(idResponsable).orElseThrow();
            factura.setResponsableDePago(pj);
        }
        else{
            factura.setResponsableDePago(h);
        }
        return repository.save(factura);
    }

    @Override
    public PersonaJuridica getPersonaJuridicaByRazonSocial(String razonSocial){
        return pjRepository.findByRazonSocial(razonSocial);
    }

    @Override
    public PersonaJuridica createPersonaJuridica(PersonaJuridica personaJuridica){
        return pjRepository.save(personaJuridica);
    }
}