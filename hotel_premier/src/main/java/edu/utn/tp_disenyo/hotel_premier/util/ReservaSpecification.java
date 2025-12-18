package edu.utn.tp_disenyo.hotel_premier.util;

import org.springframework.data.jpa.domain.Specification;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import jakarta.persistence.criteria.Predicate;

public class ReservaSpecification {

    public static Specification<Reserva> filterBy(
            String nombre,
            String apellido,
            String contacto
    ) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (nombre != null && !nombre.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                cb.upper(root.get("nombre")),
                                "%" + nombre.toUpperCase() + "%"
                        )
                );
            }

            if (apellido != null && !apellido.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                cb.upper(root.get("apellido")),
                                "%" + apellido.toUpperCase() + "%"
                        )
                );
            }

            if (contacto != null && !contacto.isBlank()) {
                predicate = cb.and(
                        predicate,
                        cb.like(
                                cb.upper(root.get("contacto")),
                                "%" + contacto.toUpperCase() + "%"
                        )
                );
            }

            return predicate;
        };
    }
}
