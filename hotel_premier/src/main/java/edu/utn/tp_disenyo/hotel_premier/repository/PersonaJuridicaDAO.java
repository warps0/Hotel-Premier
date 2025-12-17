package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.PersonaJuridica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaJuridicaDAO extends CrudRepository<PersonaJuridica, Long> {
    PersonaJuridica findByRazonSocial(String razon);
    PersonaJuridica findByCuit(String cuit);
}
