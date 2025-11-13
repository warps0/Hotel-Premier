package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
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

    // Existe un huésped con el documento pasado como argumento?
    boolean existsByDocIdentidadAndTipoDoc(String docIdentidad, TipoDoc tipoDoc);

    //public List<Huesped> findByContacto(Contacto contacto);
}
