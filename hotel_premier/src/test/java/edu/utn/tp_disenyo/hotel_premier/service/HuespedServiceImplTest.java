package edu.utn.tp_disenyo.hotel_premier.service;

import edu.utn.tp_disenyo.hotel_premier.dto.HuespedDTO;
import edu.utn.tp_disenyo.hotel_premier.exception.EntityNotSavedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedDuplicatedException;
import edu.utn.tp_disenyo.hotel_premier.exception.HuespedNotFoundException;
import edu.utn.tp_disenyo.hotel_premier.model.Huesped;
import edu.utn.tp_disenyo.hotel_premier.repository.HuespedDAO;
import edu.utn.tp_disenyo.hotel_premier.util.TipoDoc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HuespedServiceImplTest {

    @Mock
    private HuespedDAO repository;

    @InjectMocks
    private HuespedServiceImpl service;

    // --- Tests para CREATE ---

    @Test
    @DisplayName("Create debe guardar el huésped correctamente")
    void testCreateSuccess() throws EntityNotSavedException {
        Huesped huesped = new Huesped();
        huesped.setNombre("Juan");

        when(repository.save(any(Huesped.class))).thenReturn(huesped);

        Huesped result = service.create(huesped);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(repository).save(huesped);
    }

    @Test
    @DisplayName("Create debe lanzar EntityNotSavedException si el repositorio devuelve null")
    void testCreateFailure() {
        Huesped huesped = new Huesped();
        when(repository.save(any(Huesped.class))).thenReturn(null);

        assertThrows(EntityNotSavedException.class, () -> service.create(huesped));
    }

    // --- Tests para TRY TO CREATE (Validación de duplicados) ---

    @Test
    @DisplayName("TryToCreate debe lanzar excepción si el huésped ya existe")
    void testTryToCreateDuplicated() {
        Huesped huesped = new Huesped();
        huesped.setDocIdentidad("12345678");
        huesped.setTipoDoc(TipoDoc.DNI);

        when(repository.existsByDocIdentidadAndTipoDoc("12345678", TipoDoc.DNI)).thenReturn(true);

        assertThrows(HuespedDuplicatedException.class, () -> service.tryToCreate(huesped));

        // Verifica que NO se llamó a save
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("TryToCreate debe guardar si el huésped no existe")
    void testTryToCreateSuccess() throws HuespedDuplicatedException {
        Huesped huesped = new Huesped();
        huesped.setDocIdentidad("87654321");
        huesped.setTipoDoc(TipoDoc.PASAPORTE);

        when(repository.existsByDocIdentidadAndTipoDoc("87654321", TipoDoc.PASAPORTE)).thenReturn(false);
        when(repository.save(huesped)).thenReturn(huesped);

        Huesped result = service.tryToCreate(huesped);

        assertNotNull(result);
        verify(repository).save(huesped);
    }

    // --- Tests para GET ALL (Lógica compleja de filtros) ---

    @Test
    @DisplayName("GetAll sin filtros debe devolver todos los huéspedes")
    void testGetAllNoFilters() {
        Huesped h1 = new Huesped(); h1.setId(1L);
        Huesped h2 = new Huesped(); h2.setId(2L);

        when(repository.findAll()).thenReturn(List.of(h1, h2));

        List<HuespedDTO> result = service.getAll(null, null, null, null);

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("GetAll con Nombre y Apellido coincidentes (Intersección)")
    void testGetAllIntersection() {
        // Escenario: Buscamos a alguien que tenga Nombre="Juan" Y Apellido="Perez"
        // Simulamos que hay un "Juan Lopez" y un "Pedro Perez", y el "Juan Perez" correcto.

        Huesped hJuanPerez = new Huesped(); hJuanPerez.setId(1L); hJuanPerez.setNombre("Juan"); hJuanPerez.setApellido("Perez");
        Huesped hJuanLopez = new Huesped(); hJuanLopez.setId(2L); hJuanLopez.setNombre("Juan"); hJuanLopez.setApellido("Lopez");
        Huesped hPedroPerez = new Huesped(); hPedroPerez.setId(3L); hPedroPerez.setNombre("Pedro"); hPedroPerez.setApellido("Perez");

        // El repo devuelve listas mutables (ArrayList) porque el servicio usa retainAll (que modifica la lista in-place)
        when(repository.findByNombreLike("Juan")).thenReturn(new ArrayList<>(List.of(hJuanPerez, hJuanLopez)));
        when(repository.findByApellidoLike("Perez")).thenReturn(new ArrayList<>(List.of(hPedroPerez, hJuanPerez)));

        List<HuespedDTO> result = service.getAll("Juan", "Perez", null, null);

        // Debería quedar solo Juan Perez (la intersección)
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId()); // Asumiendo que HuespedDTO tiene getters
    }

    @Test
    @DisplayName("GetAll con solo Nombre (Unión implícita con lista vacía de apellidos)")
    void testGetAllSingleFilter() {
        Huesped h1 = new Huesped(); h1.setId(1L); h1.setNombre("Matias");

        when(repository.findByNombreLike("Matias")).thenReturn(new ArrayList<>(List.of(h1)));
        // No hace falta mockear apellido, doc, etc., si son null en la llamada,
        // pero dentro del if grande, las listas se inicializan vacías.

        List<HuespedDTO> result = service.getAll("Matias", null, null, null);

        assertEquals(1, result.size());
        assertEquals("Matias", result.get(0).getNombre());
    }

    // --- Tests para GET BY ID ---

    @Test
    @DisplayName("GetById debe devolver huésped si existe")
    void testGetByIdFound() throws HuespedNotFoundException {
        Huesped h = new Huesped(); h.setId(10L);
        when(repository.findById(10L)).thenReturn(Optional.of(h));

        Huesped result = service.getById(10L);
        assertEquals(10L, result.getId());
    }

    @Test
    @DisplayName("GetById debe lanzar excepción si no existe")
    void testGetByIdNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(HuespedNotFoundException.class, () -> service.getById(99L));
    }

    // --- Tests para UPDATE ---

    @Test
    @DisplayName("Update debe actualizar campos y guardar")
    void testUpdateSuccess() throws HuespedNotFoundException {
        Long id = 1L;
        Huesped existente = new Huesped();
        existente.setId(id);
        existente.setNombre("Viejo");

        Huesped nuevosDatos = new Huesped();
        nuevosDatos.setNombre("Nuevo");
        nuevosDatos.setApellido("ApellidoNuevo");
        nuevosDatos.setDocIdentidad("111");

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any(Huesped.class))).thenAnswer(i -> i.getArguments()[0]);

        Huesped result = service.update(id, nuevosDatos);

        // Verificamos que el objeto existente se modificó antes de guardar (o que se guardó lo correcto)
        assertEquals("Nuevo", existente.getNombre());
        verify(repository).save(nuevosDatos); // OJO: Tu código guarda 'nuevosDatos', no 'existente'.
    }

    // --- Tests para DELETE ---

    @Test
    @DisplayName("DeleteById elimina correctamente")
    void testDeleteSuccess() throws EntityNotSavedException {
        Huesped h = new Huesped();
        when(repository.findById(1L)).thenReturn(Optional.of(h));

        service.deleteById(1L);

        verify(repository).delete(h);
    }
}