package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.HabitacionDTO;
import edu.utn.tp_disenyo.hotel_premier.model.EstadoHabitacion;
import edu.utn.tp_disenyo.hotel_premier.model.Habitacion;
import edu.utn.tp_disenyo.hotel_premier.repository.EstadiaDAO;
import edu.utn.tp_disenyo.hotel_premier.repository.HabitacionDAO;
import edu.utn.tp_disenyo.hotel_premier.util.TipoHabitacion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitacionServiceImplTest {

    @Mock
    private HabitacionDAO habitacionRepository;

    @Mock
    private EstadiaDAO estadiaRepository;

    @Mock
    private HuespedService huespedService;

    @InjectMocks
    private HabitacionServiceImpl habitacionService;

    @Test
    @DisplayName("Create debe configurar precio y capacidad correctos para INDIVIDUAL_ESTANDAR")
    void testCreateIndividualEstandar() throws Exception {
        // Arrange
        TipoHabitacion tipo = TipoHabitacion.INDIVIDUAL_ESTANDAR;
        when(habitacionRepository.countByTipoHabitacion(tipo)).thenReturn(0);

        // Simulamos que el save devuelve la misma instancia que recibe
        when(habitacionRepository.save(any(Habitacion.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Habitacion result = habitacionService.create(tipo);

        // Assert
        ArgumentCaptor<Habitacion> captor = ArgumentCaptor.forClass(Habitacion.class);
        verify(habitacionRepository).save(captor.capture());
        Habitacion habitacionGuardada = captor.getValue();

        assertEquals(50800F, habitacionGuardada.getPrecio());
        assertEquals(1, habitacionGuardada.getCapacidad());
        assertEquals(tipo, habitacionGuardada.getTipoHabitacion());
        assertNotNull(habitacionGuardada.getHistorialEstado());
        assertNotNull(habitacionGuardada.getReservas());
    }

    @Test
    @DisplayName("Create debe configurar precio y capacidad correctos para SUPERIOR_FAMILY_PLAN")
    void testCreateSuperiorFamilyPlan() throws Exception {
        // Arrange
        TipoHabitacion tipo = TipoHabitacion.SUPERIOR_FAMILY_PLAN;
        when(habitacionRepository.countByTipoHabitacion(tipo)).thenReturn(5); // Supongamos que ya hay 5
        when(habitacionRepository.save(any(Habitacion.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Habitacion result = habitacionService.create(tipo);

        // Assert
        assertEquals(110500F, result.getPrecio());
        assertEquals(5, result.getCapacidad());
    }

    @Test
    @DisplayName("GetById debe retornar la habitación si existe")
    void testGetByIdFound() throws Exception {
        // Arrange
        Long id = 1L;
        Habitacion mockHabitacion = new Habitacion();
        mockHabitacion.setId(id);
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(mockHabitacion));

        // Act
        Optional<Habitacion> result = habitacionService.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("GetById debe lanzar excepción si no existe")
    void testGetByIdNotFound() {
        // Arrange
        Long id = 99L;
        when(habitacionRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(Exception.class, () -> habitacionService.getById(id));
    }

    @Test
    @DisplayName("DeleteById debe eliminar la habitación si existe")
    void testDeleteById() throws Exception {
        // Arrange
        Long id = 1L;
        Habitacion mockHabitacion = new Habitacion();
        when(habitacionRepository.findById(id)).thenReturn(Optional.of(mockHabitacion));

        // Act
        habitacionService.deleteById(id);

        // Assert
        verify(habitacionRepository, times(1)).delete(mockHabitacion);
    }

    @Test
    @DisplayName("UpdateById debe actualizar los campos de la habitación existente")
    void testUpdateById() throws Exception {
        // Arrange
        Long id = 1L;
        Habitacion habitacionExistente = new Habitacion();
        habitacionExistente.setId(id);
        habitacionExistente.setPrecio(100F);

        Habitacion datosNuevos = new Habitacion();
        datosNuevos.setPrecio(200F);
        datosNuevos.setCapacidad(3);
        datosNuevos.setTipoHabitacion(TipoHabitacion.SUITE_DOBLE);

        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacionExistente));

        // Act
        habitacionService.updateById(id, datosNuevos);

        // Assert
        // Nota: Como tu método update no llama a 'save' explícitamente (confía en JPA Transactional),
        // verificamos que el objeto recuperado haya sido modificado.
        assertEquals(200F, habitacionExistente.getPrecio());
        assertEquals(3, habitacionExistente.getCapacidad());
        assertEquals(TipoHabitacion.SUITE_DOBLE, habitacionExistente.getTipoHabitacion());
    }

    @Test
    @DisplayName("AgregarEstado debe añadir el estado y guardar")
    void testAgregarEstado() throws Exception {
        // Arrange
        Long id = 1L;
        Habitacion habitacionMock = new Habitacion();
        habitacionMock.setId(id);
        habitacionMock.setHistorialEstado(new ArrayList<>());

        EstadoHabitacion nuevoEstado = new EstadoHabitacion();

        when(habitacionRepository.findById(id)).thenReturn(Optional.of(habitacionMock));
        when(habitacionRepository.save(any(Habitacion.class))).thenReturn(habitacionMock);

        // Act
        habitacionService.agregarEstado(id, nuevoEstado);

        // Assert
        assertEquals(1, habitacionMock.getHistorialEstado().size());
        assertEquals(id, nuevoEstado.getHabitacion()); // Verifica que se seteo el ID en el estado
        verify(habitacionRepository).save(habitacionMock);
    }

    @Test
    @DisplayName("GetHabitacionesByRangoFecha debe filtrar estados correctamente")
    void testGetHabitacionesByRangoFecha() {
        // Arrange
        LocalDateTime inicioBusqueda = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime finBusqueda = LocalDateTime.of(2023, 10, 5, 10, 0);

        // Caso 1: Estado que se solapa (Debe aparecer)
        EstadoHabitacion estadoOcupado = new EstadoHabitacion();
        estadoOcupado.setFechaInicio(LocalDateTime.of(2023, 10, 2, 10, 0));
        estadoOcupado.setFechaFin(LocalDateTime.of(2023, 10, 3, 10, 0));

        // Caso 2: Estado fuera de rango (pasado) (NO debe aparecer)
        EstadoHabitacion estadoPasado = new EstadoHabitacion();
        estadoPasado.setFechaInicio(LocalDateTime.of(2023, 9, 1, 10, 0));
        estadoPasado.setFechaFin(LocalDateTime.of(2023, 9, 5, 10, 0));

        Habitacion habitacion = new Habitacion();
        habitacion.setId(1L);
        habitacion.setTipoHabitacion(TipoHabitacion.DOBLE_ESTANDAR);
        habitacion.setHistorialEstado(Arrays.asList(estadoOcupado, estadoPasado));

        when(habitacionRepository.findAll()).thenReturn(List.of(habitacion));

        // Act
        List<HabitacionDTO> resultado = habitacionService.getHabitacionesByRangoFecha(inicioBusqueda, finBusqueda);

        // Assert
        assertFalse(resultado.isEmpty());
        HabitacionDTO dto = resultado.get(0);

        // Validamos que de los 2 estados, solo quedó el que se solapa con la búsqueda
        assertEquals(1, dto.getHistorialEstado().size());
        assertEquals(estadoOcupado, dto.getHistorialEstado().get(0));
    }
}