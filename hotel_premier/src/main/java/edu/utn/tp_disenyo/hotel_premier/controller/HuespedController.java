package edu.utn.tp_disenyo.hotel_premier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/huesped")
public class HuespedController {
    @Autowired
    private HuespedService huespedService;

    @PostMapping("/crearHuesped")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    //TODO: Servicio, Excepciones
}
