package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotSavedException;
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
        if(huespedService.existsByDocumento(formHuesped.getDocIdentidad(), formHuesped.getTipoDoc())){
            //TODO: #1 ERROR HUESPED DUPLICADO - FLUJO ALTERNATIVO

            return "redirect:/huesped/alta/confirmacion";
        }

        // FLUJO PRINCIPAL
        huespedService.create(formHuesped);

        redirectAttributes.addFlashAttribute("nombreCompleto", formHuesped.getNombre() + " " + formHuesped.getApellido());
        redirectAttributes.addFlashAttribute("huespedGuardado", true);

        return "exitoAltaHuesped";
    }

    @GetMapping("/huesped/alta/confirmacion")
    public String confirmarHuesped() {
        //TODO: #1 /huesped/alta/confirmacion HTML
        return new String();
    }
    
}
