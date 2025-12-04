package edu.utn.tp_disenyo.hotel_premier.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import edu.utn.tp_disenyo.hotel_premier.model.Reserva;
import jakarta.persistence.criteria.Predicate;

public class ReservaSpecification {

    public static Specification<Reserva> filterBy(String nombre, String apellido, String contacto) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                predicates.add(cb.equal(root.get("nombre"), nombre));
            }

            if (apellido != null && !apellido.isBlank()) {
                predicates.add(cb.equal(root.get("apellido"), apellido));
            }

            if (contacto != null && !contacto.isBlank()) {
                predicates.add(cb.equal(root.get("contacto"), contacto));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}