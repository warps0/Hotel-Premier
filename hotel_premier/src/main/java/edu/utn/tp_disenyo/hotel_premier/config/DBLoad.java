package edu.utn.tp_disenyo.hotel_premier.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.utn.tp_disenyo.hotel_premier.model.Contacto;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
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

            con.setCorreo("NICOLAS.FRANCHUTE@GMAIL.COM");
            con.setDomicilio("BV. GALVEZ 1020");
            con.setLocalidad("SANTA FE");
            con.setPais("ARGENTINA");
            con.setTelefono("+543422222222");

            temp3.setNacionalidad("ARGENTINA");
            temp3.setOcupacion("EMPLEADO");
            temp3.setPosIva("MONOTRIBUTISTA");

            temp3.setFechaNacimiento(dob3);
            temp3.setMediosDeContacto(con3);

            huespedRepository.save(temp3);
        };
    }

    
}
