package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import lombok.NonNull;

@Service
public class HuespedServiceImpl implements HuespedService {
    @Autowired 
    private HuespedDAO huespedRepository;

    @Override
    public Huesped createHuesped(@NonNull Huesped huesped) throws Exception {
        return Optional.ofNullable(huespedRepository.save(huesped)).orElseThrow(
            () -> new Exception("Error al crear HUESPED")
        );
    }
    
}
