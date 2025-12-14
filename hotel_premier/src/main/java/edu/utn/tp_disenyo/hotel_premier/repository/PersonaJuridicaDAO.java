package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonaJuridicaDAO extends CrudRepository<PersonaJuridica, Long> {
    public List<PersonaJuridica> findByRazonSocial(String razon);

    PersonaJuridica getPersonaJuridicaById(Long id);
}
