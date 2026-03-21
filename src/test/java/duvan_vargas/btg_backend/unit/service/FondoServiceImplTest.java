package duvan_vargas.btg_backend.unit.service;



import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.repository.FondoRepository;
import duvan_vargas.btg_backend.service.impl.FondoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FondoServiceImplTest {

    @Mock
    private FondoRepository fondoRepository;

    @InjectMocks
    private FondoServiceImpl fondoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateFondo_whenNameDoesNotExist() {

        Fondo fondo = new Fondo();
        fondo.setNombre("Fondo A");

        when(fondoRepository.existsByNombre("Fondo A"))
                .thenReturn(false);

        when(fondoRepository.save(any(Fondo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Fondo result = fondoService.createFondo(fondo);

        assertNotNull(result);
        assertEquals("Fondo A", result.getNombre());
        assertEquals(0L, result.getValorTotalVinculaciones());

        verify(fondoRepository).existsByNombre("Fondo A");
        verify(fondoRepository).save(fondo);
    }

    @Test
    void shouldThrowException_whenFondoAlreadyExists() {

        Fondo fondo = new Fondo();
        fondo.setNombre("Fondo A");

        when(fondoRepository.existsByNombre("Fondo A"))
                .thenReturn(true);

        ValidacionException exception = assertThrows(
                ValidacionException.class,
                () -> fondoService.createFondo(fondo)
        );

        assertEquals(
                "Ya existe un fondo creado con el mismo nombre.",
                exception.getMessage()
        );

        verify(fondoRepository).existsByNombre("Fondo A");
        verify(fondoRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllFondos() {

        Fondo fondo1 = new Fondo();
        fondo1.setNombre("Fondo A");

        Fondo fondo2 = new Fondo();
        fondo2.setNombre("Fondo B");

        when(fondoRepository.findAll())
                .thenReturn(List.of(fondo1, fondo2));

        List<Fondo> result = fondoService.findAllFondo();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(fondoRepository).findAll();
    }
}
