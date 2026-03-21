package duvan_vargas.btg_backend.unit.service;

import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUser_whenUsernameExists() {

        Usuario usuario = new Usuario();
        usuario.setUsername("duv");

        when(usuarioRepository.findByUsername("duv"))
                .thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findByUsername("duv");

        assertNotNull(result);
        assertEquals("duv", result.getUsername());

        verify(usuarioRepository).findByUsername("duv");
    }

    @Test
    void shouldThrowException_whenUsernameNotFound() {

        when(usuarioRepository.findByUsername("xxx"))
                .thenReturn(Optional.empty());

        ValidacionException exception = assertThrows(
                ValidacionException.class,
                () -> usuarioService.findByUsername("xxx")
        );

        assertEquals(
                "El username proporcionado no se encuentra registrado en la base de datos.",
                exception.getMessage()
        );

        verify(usuarioRepository).findByUsername("xxx");
    }

    @Test
    void shouldReturnUser_whenEmailExists() {

        Usuario usuario = new Usuario();
        usuario.setEmail("test@gmail.com");

        when(usuarioRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findByEmail("test@gmail.com");

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());

        verify(usuarioRepository).findByEmail("test@gmail.com");
    }

    @Test
    void shouldThrowException_whenEmailNotFound() {

        when(usuarioRepository.findByEmail("noExiste@gmail.com"))
                .thenReturn(Optional.empty());

        ValidacionException exception = assertThrows(
                ValidacionException.class,
                () -> usuarioService.findByEmail("noExiste@gmail.com")
        );

        assertEquals(
                "El email proporcionado no se encuentra registrado en la base de datos.",
                exception.getMessage()
        );

        verify(usuarioRepository).findByEmail("noExiste@gmail.com");
    }
}
