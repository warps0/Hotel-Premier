package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Primary
public class HuespedViewController {
    @Autowired
    HuespedService huespedService;

    @GetMapping("/huesped/alta")
    public String altaHuesped(Model model) {
        model.addAttribute("message", "Dar de alta Huésped!");
        model.addAttribute("huesped", new Huesped());
        return "altaHuesped";
    }

    // TODO: Implementar método POST para guardar un huesped y renderizar la vista de "El usuario se ha cargado correctamente."

    @PostMapping(path = "/huesped", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String guardarHuesped(@RequestParam MultiValueMap<String, String> formData) {
        
        //huespedService.create();

        return "exitoAltaHuesped";
    }

}
