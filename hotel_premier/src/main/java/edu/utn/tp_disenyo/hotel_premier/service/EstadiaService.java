package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaServicioDTO;

public interface EstadiaService {
    public List<EstadiaServicioDTO> findEstadiaServicioByIdEstadia(Long idEstadia);
}
