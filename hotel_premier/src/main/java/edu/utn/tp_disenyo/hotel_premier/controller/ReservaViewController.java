package edu.utn.tp_disenyo.hotel_premier.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.service.ReservaService;

@Controller
public class ReservaViewController {

	private final ReservaService reservaService;

	public ReservaViewController(ReservaService reservaService) {
		this.reservaService = reservaService;
	}

	// Mostrar la página de confirmación (ya existe la plantilla `confirmarReserva.html`)
	@GetMapping("/confirmar/reserva/view")
	public String mostrarConfirmacionVista(Model model) {
		return "confirmarReserva";
	}

	// Endpoint para recibir la reserva desde la vista (JSON). Esta ruta no reemplaza
	// al REST controller `/api/reserva` existente, pero sirve para llamadas desde la UI
	@PostMapping(value = "/reservas/crear", consumes = "application/json")
	@ResponseBody
	public ResponseEntity<?> crearReservaDesdeVista(@RequestBody ReservaCreateDTO reservaCreate) {
		try {
			ReservaDTO creada = reservaService.create(reservaCreate);
			return new ResponseEntity<>(creada, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
