package edu.utn.tp_disenyo.hotel_premier.controller;


import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;

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

    public HuespedViewController(HuespedService huespedService) {
        this.huespedService = huespedService;
    }

    @GetMapping("/huesped/alta")
    public String altaHuesped(Model model) {
        model.addAttribute("huesped", new Huesped());

        return "altaHuesped";
    }

    @GetMapping("/huesped/exito")
    public String exitoAltaHuesped() {

        return "exitoAltaHuesped";
    }

    @GetMapping("/huesped/duplicado")
    public String confirmarHuesped(@ModelAttribute("huesped") Huesped formHuesped, Model model) {
        return "advertenciaDNI";
    }

    @PostMapping("/huesped")
    public String submitForm(@ModelAttribute Huesped formHuesped, RedirectAttributes redirectAttributes) throws HuespedNotSavedException {
        try{
            huespedService.tryToCreate(formHuesped);

            redirectAttributes.addFlashAttribute("nombreCompleto", formHuesped.getNombre() + " " + formHuesped.getApellido());

            return "redirect:/huesped/exito";
        }
        catch(HuespedDuplicatedException e){

            redirectAttributes.addFlashAttribute("huesped", formHuesped);
            return "redirect:/huesped/duplicado";
        }

    }

    @PostMapping("/huesped/forzarCreacion")
    public String forzarCreacion(@ModelAttribute("huesped") Huesped formHuesped, RedirectAttributes redirectAttributes) throws HuespedNotSavedException {
        huespedService.create(formHuesped);

        redirectAttributes.addFlashAttribute("nombreCompleto", formHuesped.getNombre() + " " + formHuesped.getApellido());

        return "redirect:/huesped/exito";
    }
}
