package edu.utn.tp_disenyo.hotel_premier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/huesped")
public class HuespedController {
    @Autowired
    private HuespedService huespedService;

    @GetMapping("/")
    public String helloWorld() {
        return new String("Hello world!");
    }
    

    @PostMapping("/crearHuesped")
    public Huesped postMethodName(@RequestBody Huesped huesped) throws Exception {
        //TODO: process POST request with HuespedDTO
        // Esto para que el servicio y repositorio sean los encargados de manejar el objeto persistente
      
        return huespedService.createHuesped(huesped);
    }
    
    //TODO: Servicio, Excepciones
}
