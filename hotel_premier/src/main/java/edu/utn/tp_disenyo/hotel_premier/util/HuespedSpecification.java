package edu.utn.tp_disenyo.hotel_premier.util;

import org.springframework.data.jpa.domain.Specification;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;

import jakarta.persistence.criteria.Predicate;

public class HuespedSpecification {

    public static Specification<Huesped> filter(
            String nombre,
            String apellido,
            String documento,
            TipoDoc tipoDoc
    ) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction(); // AND base

            if (nombre != null && !nombre.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                cb.lower(root.get("nombre")),
                                "%" + nombre.toLowerCase() + "%"
                        )
                );
            }

            if (apellido != null && !apellido.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                cb.lower(root.get("apellido")),
                                "%" + apellido.toLowerCase() + "%"
                        )
                );
            }

            if (documento != null && !documento.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                root.get("docIdentidad"),
                                "%" + documento + "%"
                        )
                );
            }

            if (tipoDoc != null) {
                predicate = cb.and(
                        predicate,
                        cb.equal(root.get("tipoDoc"), tipoDoc)
                );
            }

            return predicate;
        };
    }
}
