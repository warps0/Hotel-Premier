package edu.utn.tp_disenyo.hotel_premier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaServicioDTO;
import edu.utn.tp_disenyo.hotel_premier.model.Estadia;
import edu.utn.tp_disenyo.hotel_premier.model.EstadiaServicio;
import edu.utn.tp_disenyo.hotel_premier.model.Servicio;

import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaServicioDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.ServicioDAO;


public class EstadiaServiceImpl implements EstadiaService {
    EstadiaDAO estadiaRepository;
    ServicioDAO servicioDAO;
    EstadiaServicioDAO estadiaServicioDAO;

    EstadiaServiceImpl(EstadiaDAO estadiaRepository, ServicioDAO servicioDAO, EstadiaServicioDAO estadiaServicioDAO) {
        this.estadiaRepository = estadiaRepository;
        this.servicioDAO = servicioDAO;
        this.estadiaServicioDAO = estadiaServicioDAO;
    }

    @Override
    public Estadia findEstadiaById(Long idEstadia) {
        return estadiaRepository.findById(idEstadia).orElseThrow();
    }

    @Override
    public List<EstadiaServicioDTO> findEstadiaServicioByIdEstadia(Long idEstadia) {
        List<EstadiaServicio> estadiasServicios = estadiaServicioDAO.findAllByEstadia(idEstadia);

        List<EstadiaServicioDTO> esDTO = new ArrayList<>();

        for(EstadiaServicio es : estadiasServicios) {
            Servicio tempServ = servicioDAO.findById(es.getServicioId()).orElseThrow();

            EstadiaServicioDTO temp = new EstadiaServicioDTO(idEstadia, tempServ, es.getIncluido());

            esDTO.add(temp);
        }

        return esDTO;
    }
}
