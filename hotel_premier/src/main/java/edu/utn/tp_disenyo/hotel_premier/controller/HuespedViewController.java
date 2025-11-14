package edu.utn.tp_disenyo.hotel_premier.controller;


import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String submitForm(@ModelAttribute Huesped formHuesped, RedirectAttributes redirectAttributes) throws HuespedNotSavedException {
        try{
            huespedService.tryToCreate(formHuesped);

            redirectAttributes.addFlashAttribute("nombreCompleto", formHuesped.getNombre() + " " + formHuesped.getApellido());
            redirectAttributes.addFlashAttribute("huespedGuardado", true);

            return "redirect:/huesped/exito";
        }
        catch(HuespedDuplicatedException e){

            redirectAttributes.addFlashAttribute("huespedDuplicado", formHuesped);
            return "redirect:/huesped/duplicado";
        }

    }

    @GetMapping("/huesped/exito")
    public String exitoAltaHuesped() {

        return "exitoAltaHuesped";
    }

    @GetMapping("/huesped/duplicado")
    public String confirmarHuesped(@ModelAttribute Huesped formHuesped) {
        return "advertenciaDNI";
    }

    @PostMapping("/huesped/forzarCreacion")
    public String forzarCreacion(@ModelAttribute Huesped formHuesped) throws HuespedNotSavedException {
        huespedService.create(formHuesped);

        return "redirect:/huesped/exito";
    }
}
