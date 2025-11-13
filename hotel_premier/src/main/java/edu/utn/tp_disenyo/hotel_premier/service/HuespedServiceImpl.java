package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import lombok.NonNull;

@Service
public class HuespedServiceImpl implements HuespedService {

    private final HuespedDAO repository;

    @Autowired
    public HuespedServiceImpl(HuespedDAO repository) {
        this.repository = repository;
    }

    // TODO: Manejar excepciones para el método create
    @Override
    public Huesped create(@NonNull Huesped huesped) throws Exception {

        return Optional.ofNullable(repository.save(huesped)).orElseThrow(
            () -> new HuespedNotSavedException()
        );
    }

    @Override
    public List<Huesped> getAll() {
        return repository.findAll();
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
    public void deleteById(Long id) throws HuespedNotFoundException {
        Huesped huesped = repository.findById(id).orElseThrow(() -> new HuespedNotFoundException());
        repository.delete(huesped);
    }

    @Override
    public boolean existsByDocumento(String docIdentidad, TipoDoc tipoDoc) {
        return repository.existsByDocIdentidadAndTipoDoc(docIdentidad, tipoDoc);
    }

}
