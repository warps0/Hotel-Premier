package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.List;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaServicioDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Estadia;

public interface EstadiaService {
    public Estadia findEstadiaById(Long idEstadia);
    public List<EstadiaServicioDTO> findEstadiaServicioByIdEstadia(Long idEstadia);
}
