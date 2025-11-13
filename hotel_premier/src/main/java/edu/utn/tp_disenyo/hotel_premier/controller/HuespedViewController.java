package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Primary
public class HuespedViewController {

    HuespedService huespedService;

    @Autowired
    public HuespedViewController(HuespedService huespedService) {
        this.huespedService = huespedService;
    }

    @GetMapping("/huesped/alta")
    public String altaHuesped(Model model) {
        model.addAttribute("huesped", new Huesped());
        return "altaHuesped";
    }

    // TODO: Implementar la vista de "El usuario se ha cargado correctamente."

    @PostMapping("/huesped")
    public String submitForm(@ModelAttribute Huesped formHuesped, Model model) throws Exception {
        huespedService.create(formHuesped);
        return "exitoAltaHuesped"; // Display a result page
    }
}
