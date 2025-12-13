package edu.utn.tp_disenyo.hotel_premier.repository;

import edu.utn.tp_disenyo.hotel_premier.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaDAO extends JpaRepository<Factura, Long> {
}
