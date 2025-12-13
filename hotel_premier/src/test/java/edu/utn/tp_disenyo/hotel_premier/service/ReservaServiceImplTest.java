package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.EstadiaDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaCreateDTO;
import edu.utn.tp_disenyo.hotel_premier.dto.ReservaDTO;
import edu.utn.tp_disenyo.hotel_premier.model.*;
import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.ReservaDAO;
import edu.utn.tp_disenyo.hotel_premier.util.Estado;
import edu.utn.tp_disenyo.hotel_premier.util.EstadoReserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceImplTest {

    @Mock
    private ReservaDAO reservaRepository;
    @Mock
    private EstadiaDAO estadiaRepository;
    @Mock
    private HuespedService huespedService;
    @Mock
    private HabitacionService habitacionService;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    // --- CREATE ---

    @Test
    @DisplayName("Create debe crear reserva si la habitación está disponible")
    void testCreateSuccess() throws Exception {
        // Arrange
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(5);
        Long idHabitacion = 1L;
        Long idHuesped = 10L;

        ReservaCreateDTO createDTO = new ReservaCreateDTO();
        createDTO.setFechaInicio(inicio);
        createDTO.setFechaFin(fin);
        createDTO.setHabitacionesIds(Collections.singletonList(idHabitacion));
        createDTO.setHuespedesIds(Collections.singletonList(idHuesped));

        // Mock: Habitación libre (historial de estados vacío en el rango)
        HabitacionDTO habitacionDTOLibre = new HabitacionDTO();
        habitacionDTOLibre.setHistorialEstado(new ArrayList<>());
        when(habitacionService.getHabitacionByRangoFecha(idHabitacion, inicio, fin)).thenReturn(habitacionDTOLibre);

        // Mock: Obtener entidad Habitación
        Habitacion habitacion = new Habitacion();
        habitacion.setId(idHabitacion);
        when(habitacionService.getById(idHabitacion)).thenReturn(Optional.of(habitacion));

        // Mock: Obtener Huéspedes
        Huesped huesped = new Huesped();
        huesped.setId(idHuesped);
        when(huespedService.findAllByIds(anyList())).thenReturn(List.of(huesped));

        // Mock: Save
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ReservaDTO result = reservaService.create(createDTO);

        // Assert
        assertNotNull(result);
        verify(habitacionService).agregarEstado(eq(idHabitacion), any(EstadoHabitacion.class)); // Verifica que se bloqueó la habitación
        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Create debe fallar si la habitación no está disponible")
    void testCreateFailOccupied() throws Exception {
        // Arrange
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = LocalDateTime.now().plusDays(2);
        Long idHabitacion = 1L;

        ReservaCreateDTO createDTO = new ReservaCreateDTO();
        createDTO.setFechaInicio(inicio);
        createDTO.setFechaFin(fin);
        createDTO.setHabitacionesIds(Collections.singletonList(idHabitacion));

        // Mock: Habitación ocupada (la lista de estados NO está vacía)
        HabitacionDTO habitacionDTOOcupada = new HabitacionDTO();
        habitacionDTOOcupada.setHistorialEstado(List.of(new EstadoHabitacion()));
        when(habitacionService.getHabitacionByRangoFecha(idHabitacion, inicio, fin)).thenReturn(habitacionDTOOcupada);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> reservaService.create(createDTO));
        assertTrue(exception.getMessage().contains("no está disponible"));

        verify(reservaRepository, never()).save(any());
    }

    // --- OCUPAR HABITACION ---

    @Test
    @DisplayName("OcuparHabitacion debe generar estadía y cambiar estados")
    void testOcuparHabitacion() throws Exception {
        // Arrange
        Long idReserva = 100L;
        Long idHabitacion = 1L;
        Long idHuesped = 50L;
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = LocalDateTime.now().plusDays(3);

        // Configurar Reserva
        Reserva reserva = new Reserva();
        reserva.setId(idReserva);
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);
        reserva.setEstado(EstadoReserva.EXISTENTE);

        // Configurar Habitación con estado RESERVADO
        Habitacion habitacion = new Habitacion();
        habitacion.setId(idHabitacion);

        EstadoHabitacion estadoReservado = new EstadoHabitacion(inicio, fin, Estado.RESERVADO);
        // Simulamos que la habitación tiene este estado (dependiendo de tu implementación de getEstadoHabitacion)
        habitacion.setHistorialEstado(new ArrayList<>(List.of(estadoReservado)));

        reserva.setHabitaciones(Collections.singletonList(habitacion));

        when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reserva));
        when(habitacionService.getById(idHabitacion)).thenReturn(Optional.of(habitacion));

        Huesped huesped = new Huesped();
        huesped.setId(idHuesped);
        when(huespedService.getById(idHuesped)).thenReturn(huesped);

        // Act
        EstadiaDTO result = reservaService.ocuparHabitacion(idReserva, idHabitacion, List.of(idHuesped));

        // Assert
        assertNotNull(result);
        assertEquals(Estado.OCUPADO, estadoReservado.getEstado()); // El estado en la habitación cambió
        assertEquals(EstadoReserva.ACTIVA, reserva.getEstado());   // La reserva pasó a ACTIVA

        verify(estadiaRepository).save(any(Estadia.class));
        verify(habitacionService).updateById(eq(idHabitacion), any(Habitacion.class));
    }

    // --- CANCELAR RESERVA ---

    @Test
    @DisplayName("CancelarReserva debe liberar estados de habitación y cambiar estado reserva")
    void testCancelarReserva() throws Exception {
        // Arrange
        Long idReserva = 100L;
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = LocalDateTime.now().plusDays(3);

        Reserva reserva = new Reserva();
        reserva.setId(idReserva);
        reserva.setEstado(EstadoReserva.EXISTENTE);
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);

        Habitacion habitacion = new Habitacion();
        habitacion.setId(1L);
        EstadoHabitacion estadoReservado = new EstadoHabitacion(inicio, fin, Estado.RESERVADO);
        habitacion.setHistorialEstado(new ArrayList<>(List.of(estadoReservado)));

        reserva.setHabitaciones(List.of(habitacion));

        when(reservaRepository.findById(idReserva)).thenReturn(Optional.of(reserva));

        // Act
        reservaService.cancelarReserva(List.of(idReserva));

        // Assert
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        assertTrue(habitacion.getHistorialEstado().isEmpty()); // Se debió borrar el estado
        verify(reservaRepository).save(reserva);
        verify(habitacionService).updateById(eq(1L), any(Habitacion.class));
    }

    // --- GET BY RESPONSABLE ---

    @Test
    @DisplayName("GetByResponsable debe llamar al repositorio con Specification")
    void testGetByResponsable() throws Exception {
        // Arrange
        Reserva r = new Reserva();
        r.setId(1L);
        r.setHuespedes(new ArrayList<>()); // Evitar NullPointer en el loop
        r.setHabitaciones(new ArrayList<>());

        // Mockear que findAll con Specification devuelve una lista
        when(reservaRepository.findAll(any(Specification.class))).thenReturn(List.of(r));

        // Act
        List<ReservaDTO> result = reservaService.getByResponsable("Juan", null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservaRepository).findAll(any(Specification.class));
    }

    // --- HUESPED RESERVADO ---

    @Test
    @DisplayName("HuespedReservado debe retornar true si el huesped está en reserva activa")
    void testHuespedReservadoTrue() throws Exception {
        // Arrange
        Long idHuesped = 1L;
        Huesped huesped = new Huesped();
        huesped.setId(idHuesped);

        Reserva reservaActiva = new Reserva();
        reservaActiva.setEstado(EstadoReserva.ACTIVA);
        reservaActiva.setHuespedes(List.of(huesped));
        reservaActiva.setHabitaciones(new ArrayList<>());

        when(reservaRepository.findAll()).thenReturn(List.of(reservaActiva));
        when(huespedService.getById(idHuesped)).thenReturn(huesped);

        // Act
        boolean result = reservaService.huespedReservado(idHuesped);

        // Assert
        // NOTA: Este test depende de que HuespedDTO implemente equals() correctamente
        // o que la conversión DTO en el servicio coincida.
        assertTrue(result);
    }
}