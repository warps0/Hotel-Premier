package edu.utn.tp_disenyo.hotel_premier.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Contacto;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HabitacionDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import edu.utn.tp_disenyo.hotel_premier.service.HabitacionService;
import edu.utn.tp_disenyo.hotel_premier.service.ReservaService;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;

@Configuration
public class DBLoad {

    @Bean
    CommandLineRunner initHuesped(HuespedDAO huespedRepository) {
        return args -> {
            Huesped temp = new Huesped("MATIAS", "TROSSERO", "39504880", TipoDoc.DNI);
            LocalDate dob = LocalDate.of(1996, 11, 25);
            Contacto con = new Contacto();

            con.setCorreo("MATIAS.TROSSERO.1@GMAIL.COM");
            con.setDomicilio("HNS 1337");
            con.setLocalidad("SANTA FE");
            con.setPais("ARGENTINA");
            con.setTelefono("+543420000000");

            temp.setNacionalidad("ARGENTINA");
            temp.setOcupacion("ESTUDIANTE");
            temp.setPosIva("BOMBASTIC!");
            
            temp.setFechaNacimiento(dob);
            temp.setMediosDeContacto(con);

            huespedRepository.save(temp);

            Huesped temp2 = new Huesped("MATIAS", "BENITEZ", "40123456", TipoDoc.DNI);
            LocalDate dob2 = LocalDate.of(1998, 3, 14);
            Contacto con2 = new Contacto();

            con2.setCorreo("MATIAS.BENITEZ@GMAIL.COM");
            con2.setDomicilio("AV. SAN MARTIN 456");
            con2.setLocalidad("SANTA FE");
            con2.setPais("ARGENTINA");
            con2.setTelefono("+543421111111");

            temp2.setNacionalidad("ARGENTINA");
            temp2.setOcupacion("ESTUDIANTE");
            temp2.setPosIva("RESPONSABLE INSCRIPTO");

            temp2.setFechaNacimiento(dob2);
            temp2.setMediosDeContacto(con2);

            huespedRepository.save(temp2);

            Huesped temp3 = new Huesped("NICOLAS", "FRANCHUTE CASTALDI", "38987654", TipoDoc.DNI);
            LocalDate dob3 = LocalDate.of(1994, 7, 2);
            Contacto con3 = new Contacto();

            con3.setCorreo("NICOLAS.FRANCHUTE@GMAIL.COM");
            con3.setDomicilio("BV. GALVEZ 1020");
            con3.setLocalidad("SANTA FE");
            con3.setPais("ARGENTINA");
            con3.setTelefono("+543422222222");

            temp3.setNacionalidad("ARGENTINA");
            temp3.setOcupacion("EMPLEADO");
            temp3.setPosIva("MONOTRIBUTISTA");

            temp3.setFechaNacimiento(dob3);
            temp3.setMediosDeContacto(con3);

            huespedRepository.save(temp3);
        };
    }

    @Bean
    CommandLineRunner initHabitaciones(HabitacionService habitacionService) {
        return args -> {
            habitacionService.initHabitaciones();
        };
    }

    @Bean
    CommandLineRunner initReservas(ReservaService reservaService) {
        return args -> {
            List<Long> huespedesIds = new ArrayList<>();
            List<Long> habitacionesIds = new ArrayList<>();
            LocalDateTime fechaInicio = LocalDateTime.of(2025, 12, 16, 0, 0);
            LocalDateTime fechaFin = LocalDateTime.of(2025, 12, 22, 0, 0);;

            huespedesIds.add(1L);
            habitacionesIds.add(1L);

            String nombre = "MATIAS";
            String apellido = "TROSSERO";
            String contacto = "3420000000";
            ReservaCreateDTO reservaCreateDTO = new ReservaCreateDTO(
                huespedesIds, habitacionesIds, 
                fechaInicio, fechaFin, 
                nombre, apellido, contacto
            );

            reservaService.create(reservaCreateDTO);

            List<Long> huespedesIds2 = new ArrayList<>();
            List<Long> habitacionesIds2 = new ArrayList<>();
            LocalDateTime fechaInicio2 = LocalDateTime.of(2025, 12, 15, 0, 0);
            LocalDateTime fechaFin2 = LocalDateTime.of(2025, 12, 17, 0, 0);;

            huespedesIds2.add(2L);
            habitacionesIds2.add(2L);

            String nombre2 = "MATIAS";
            String apellido2 = "BENITEZ";
            String contacto2 = "3420000000";
            ReservaCreateDTO reservaCreateDTO2 = new ReservaCreateDTO(
                huespedesIds2, habitacionesIds2, 
                fechaInicio2, fechaFin2, 
                nombre2, apellido2, contacto2
            );

            reservaService.create(reservaCreateDTO2);

            List<Long> huespedesIds3 = new ArrayList<>();
            List<Long> habitacionesIds3 = new ArrayList<>();
            LocalDateTime fechaInicio3 = LocalDateTime.of(2025, 12, 13, 0, 0);
            LocalDateTime fechaFin3 = LocalDateTime.of(2025, 12, 19, 0, 0);;

            huespedesIds3.add(3L);
            habitacionesIds3.add(3L);

            String nombre3 = "FRANCHUTE";
            String apellido3 = "CASTALDI";
            String contacto3 = "3420000000";
            ReservaCreateDTO reservaCreateDTO3 = new ReservaCreateDTO(
                huespedesIds3, habitacionesIds3, 
                fechaInicio3, fechaFin3, 
                nombre3, apellido3, contacto3
            );

            reservaService.create(reservaCreateDTO3);
        };
    }
}
