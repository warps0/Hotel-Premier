package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HuespedDAO extends JpaRepository<Huesped, Long> {

    public List<Huesped> findByPosIva(String posIva);
    public List<Huesped> findByFechaNacimiento(LocalDate fechaNacimiento);
    public List<Huesped> findByNacionalidad(String nacionalidad);
    public List<Huesped> findByOcupacion(String ocupacion);
    public List<Huesped> findByNombre(String nombre);
    public List<Huesped> findByApellido(String apellido);
    public List<Huesped> findByDocIdentidad(String docIdentidad);
    //public List<Huesped> findByContacto(Contacto contacto);
    // public List<Persona> findByNombre(String nombre); TODO: EnumTipoDoc

}
