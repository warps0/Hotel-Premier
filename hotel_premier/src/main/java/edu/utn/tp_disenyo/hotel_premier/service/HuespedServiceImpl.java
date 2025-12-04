package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import lombok.NonNull;

@Service
public class HuespedServiceImpl implements HuespedService {

    private final HuespedDAO repository;

    public HuespedServiceImpl(HuespedDAO repository) {
        this.repository = repository;
    }

    // TODO: Manejar excepciones para el método create
    @Override
    public Huesped create(@NonNull Huesped huesped) throws EntityNotSavedException {

        return Optional.ofNullable(repository.save(huesped)).orElseThrow(
            () -> new EntityNotSavedException()
        );
    }

    @Override
    public List<HuespedDTO> getAll(String nombre, String apellido, String documento, TipoDoc tipoDoc) {
        List<Huesped> huespedes = new ArrayList<>();
        List<HuespedDTO> huespedDTOs = new ArrayList<>();

        if(nombre != null || apellido != null || documento != null || tipoDoc != null){
            List<Huesped> huespedesPorNombre = new ArrayList<>();
            List<Huesped> huespedesPorApellido = new ArrayList<>();
            List<Huesped> huespedesPorTipoDoc = new ArrayList<>();
            List<Huesped> huespedesPorDoc = new ArrayList<>();
            
            if(nombre != null){
                huespedesPorNombre = repository.findByNombre(nombre);
            }

            if(apellido != null){
                huespedesPorApellido = repository.findByApellido(apellido);
            }

            if(documento != null){
                huespedesPorDoc = repository.findByDocIdentidad(documento);
            }

            if(tipoDoc != null){
                huespedesPorTipoDoc = repository.findByTipoDoc(tipoDoc);
            }

            /*
                Nueva estrategia: 
                    #1 Unificar o Intersectar hNombre con hApellido
                    #2 Unificar o Intersectar hDoc con hTipoDoc

                    #3 Unificar o Intersectar hNombre con hDoc
            */

           // #1 TODO: Comportamiento extraño CASO 'Matias' 'Ramonda'
           if(!huespedesPorNombre.isEmpty() && !huespedesPorApellido.isEmpty()) { // INTERSECTAR
            huespedesPorNombre.retainAll(huespedesPorApellido);
           }
           else { // UNIFICAR
            huespedesPorNombre = Stream.concat(huespedesPorNombre.stream(), huespedesPorApellido.stream()).distinct().collect(Collectors.toList());
           }

           // #2
           if(!huespedesPorDoc.isEmpty() && !huespedesPorTipoDoc.isEmpty()) { // INTERSECTAR
            huespedesPorDoc.retainAll(huespedesPorTipoDoc);
           }
           else { // UNIFICAR
            huespedesPorDoc = Stream.concat(huespedesPorDoc.stream(), huespedesPorTipoDoc.stream()).distinct().collect(Collectors.toList());
           }

           // #3
           if(!huespedesPorNombre.isEmpty() && !huespedesPorDoc.isEmpty()) { // INTERSECTAR
            huespedesPorNombre.retainAll(huespedesPorDoc);
           }
           else { // UNIFICAR
            huespedes = Stream.concat(huespedesPorNombre.stream(), huespedesPorDoc.stream()).distinct().collect(Collectors.toList());
           }
        }
        else {
            huespedes = repository.findAll();
        }

        for (Huesped huesped : huespedes) {
            huespedDTOs.add(
                new HuespedDTO(huesped.getId(), huesped.getNombre(), huesped.getApellido(), huesped.getDocIdentidad(), huesped.getTipoDoc()
            ));
        }        
        
        return huespedDTOs;
    }

    @Override
    public Huesped getById(Long id) throws HuespedNotFoundException {
        return repository.findById(id).orElseThrow(() -> new HuespedNotFoundException());
    }

    // TODO: update Huesped buscando por nombre/apellido/docIdentidad? - Manejar ID
    @Override
    public Huesped update(Long id, @NonNull Huesped huesped) throws HuespedNotFoundException {
        Huesped actual = repository.findById(id).orElseThrow(() -> new HuespedNotFoundException());

        actual.setApellido(huesped.getApellido());
        actual.setNombre(huesped.getNombre());
        actual.setDocIdentidad(huesped.getDocIdentidad());
        return repository.save(huesped);
    }

    @Override
    public void deleteById(Long id) throws EntityNotSavedException {
        Huesped huesped = repository.findById(id).orElseThrow(() -> new EntityNotSavedException());
        repository.delete(huesped);
    }

    @Override
    public boolean existsByDocumento(String docIdentidad, TipoDoc tipoDoc) {
        return repository.existsByDocIdentidadAndTipoDoc(docIdentidad, tipoDoc);
    }

    public Huesped tryToCreate(Huesped huesped) throws HuespedDuplicatedException{
        if(this.existsByDocumento(huesped.getDocIdentidad(), huesped.getTipoDoc())){
            //#1 ERROR HUESPED DUPLICADO - FLUJO ALTERNATIVO

            throw new HuespedDuplicatedException();
        }

        return repository.save(huesped);
    }

    @Override
    public List<Huesped> findAllByIds(List<Long> ids) {
        List<Huesped> huespedes = repository.findAllById(ids);

        return huespedes;
    }
}
