package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HuespedViewController {

    @GetMapping("/alta")
    public String altaHuesped(Model model) {
        model.addAttribute("message", "Dar de alta Huésped!");
        model.addAttribute("huesped", new Huesped());
        return "altaHuesped";
    }

    // TODO: Implementar método POST para guardar un huesped y renderizar la vista de "El usuario se ha cargado correctamente."

//    @PostMapping(path = "/huesped", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public String guardarHuesped(@RequestParam MultiValueMap<String, String> formData) {
//    }

}
