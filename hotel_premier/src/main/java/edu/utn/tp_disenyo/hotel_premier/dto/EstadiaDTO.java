package edu.utn.tp_disenyo.hotel_premier.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EstadiaDTO {

    private Long id;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaEgreso;
    private ReservaDTO reserva;
    private List<HuespedDTO> huespedes;
    private Long habitacionId;

}
