package edu.utn.tp_disenyo.hotel_premier.controller;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.service.HuespedService;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
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

    @GetMapping("/huesped/buscar")
    public String buscarHuesped(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String apellido,
        @RequestParam(required = false) String documento,
        @RequestParam(required = false) TipoDoc tipoDocumento,
        @RequestParam(required = false, defaultValue = "false") boolean buscar, Model model) {
    
        // Si no se ha presionado buscar, mostrar solo el formulario
        if (!buscar) {
            return "buscarHuesped";
        }
        
        // Limpiar parámetros vacíos
        nombre = (nombre != null && nombre.trim().isEmpty()) ? null : nombre;
        apellido = (apellido != null && apellido.trim().isEmpty()) ? null : apellido;
        documento = (documento != null && documento.trim().isEmpty()) ? null : documento;
        tipoDocumento = (tipoDocumento != null) ? tipoDocumento : null;
        
        // Hacer la búsqueda (con o sin filtros)
        List<HuespedDTO> resultados = huespedService.getAll(nombre, apellido, documento, tipoDocumento);
        
        model.addAttribute("resultados", resultados);
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);
        model.addAttribute("docIdentidad", documento);
        model.addAttribute("tipoDoc", tipoDocumento);
        
        return "seleccionarHuesped";
    }

    @PostMapping("/huesped/seleccionar")
    public String seleccionarHuesped(@RequestParam Long huespedId, RedirectAttributes redirectAttributes) 
    throws HuespedNotFoundException, EntityNotSavedException {
        //TODO: TRYCACH
        Huesped huespedSeleccionado = huespedService.getById(huespedId);
        redirectAttributes.addFlashAttribute("huespedSeleccionado", huespedSeleccionado);
        
        // Redirigir a donde necesites con el huésped seleccionado
        return "redirect:/habitaciones/reservar";
    }

    @GetMapping("/huesped/duplicado")
    public String confirmarHuesped(@ModelAttribute("huesped") Huesped formHuesped, Model model) {
        return "advertenciaDNI";
    }

    // TODO: cambiar de controlador
    @GetMapping({ "/", "/home" })
    public String home() {
        return "index";
    }

    @PostMapping("/huesped")
    public String submitForm(@ModelAttribute Huesped formHuesped, RedirectAttributes redirectAttributes)
            throws EntityNotSavedException {
        try {
            huespedService.tryToCreate(formHuesped);

            redirectAttributes.addFlashAttribute("nombreCompleto",
                    formHuesped.getNombre() + " " + formHuesped.getApellido());

            return "redirect:/huesped/exito";
        } catch (HuespedDuplicatedException e) {

            redirectAttributes.addFlashAttribute("huesped", formHuesped);
            return "redirect:/huesped/duplicado";
        }
    }

    @PostMapping("/huesped/forzarCreacion")
    public String forzarCreacion(@ModelAttribute("huesped") Huesped formHuesped, RedirectAttributes redirectAttributes)
            throws EntityNotSavedException {
        huespedService.create(formHuesped);

        redirectAttributes.addFlashAttribute("nombreCompleto",
                formHuesped.getNombre() + " " + formHuesped.getApellido());

        return "redirect:/huesped/exito";
    }
}
