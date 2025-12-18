package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HuespedDAO extends JpaRepository<Huesped, Long>, JpaSpecificationExecutor<Huesped> {
    public List<Huesped> findByPosIvaLike(String posIva);
    public List<Huesped> findByFechaNacimiento(LocalDate fechaNacimiento);
    public List<Huesped> findByNacionalidadLike(String nacionalidad);
    public List<Huesped> findByOcupacionLike(String ocupacion);
    public List<Huesped> findByNombreLike(String nombre);
    public List<Huesped> findByApellidoLike(String apellido);
    public List<Huesped> findByDocIdentidadLike(String docIdentidad);
    public List<Huesped> findByTipoDoc(TipoDoc tipoDoc);

    // Existe un huésped con el documento pasado como argumento?
    boolean existsByDocIdentidadAndTipoDoc(String docIdentidad, TipoDoc tipoDoc);

    //public List<Huesped> findByContacto(Contacto contacto);
    // List<Huesped> findAllById(List<Long> ids);
}
