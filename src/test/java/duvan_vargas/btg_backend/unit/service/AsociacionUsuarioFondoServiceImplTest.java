package duvan_vargas.btg_backend.unit.service;

import duvan_vargas.btg_backend.exception.DataNotFoundException;
import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.repository.AsociacionUsuarioFondoRepository;
import duvan_vargas.btg_backend.repository.FondoRepository;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.impl.AsociacionUsuarioFondoServiceImpl;
import duvan_vargas.btg_backend.util.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsociacionUsuarioFondoServiceImplTest {

    @Mock
    private AsociacionUsuarioFondoRepository asociacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FondoRepository fondoRepository;

    @Mock
    private SmsService smsService;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private AsociacionUsuarioFondoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAperturaSuccessfully() {

        AsociacionUsuarioFondo asociacion = new AsociacionUsuarioFondo();
        asociacion.setIdUsuario("1");
        asociacion.setIdFondo("1");
        asociacion.setTipoVinculacion("APERTURA");
        asociacion.setNotifiacionEmail(false);

        Usuario usuario = new Usuario();
        usuario.setMontoInicial(1000000L);
        usuario.setNumeroTelefono("+573000000000");

        Fondo fondo = new Fondo();
        fondo.setNombre("Fondo A");
        fondo.setMontoMinimo(500000L);
        fondo.setValorTotalVinculaciones(0L);

        when(fondoRepository.findById("1")).thenReturn(Optional.of(fondo));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(asociacionRepository.existsByIdUsuarioAndIdFondoAndActivo("1", "1", true)).thenReturn(false);
        when(asociacionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AsociacionUsuarioFondo result = service.createAsociacion(asociacion);

        assertNotNull(result);
        assertTrue(result.getActivo());
        assertEquals(500000L, result.getMontoPagado());

        verify(smsService).enviarSms(any(), contains("Fondo A"));
    }

    @Test
    void shouldThrowException_whenNoSaldo() {

        AsociacionUsuarioFondo asociacion = new AsociacionUsuarioFondo();
        asociacion.setIdUsuario("1");
        asociacion.setIdFondo("1");
        asociacion.setTipoVinculacion("APERTURA");

        Usuario usuario = new Usuario();
        usuario.setMontoInicial(1000L);

        Fondo fondo = new Fondo();
        fondo.setNombre("Fondo A");
        fondo.setMontoMinimo(500000L);

        when(fondoRepository.findById("1")).thenReturn(Optional.of(fondo));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(asociacionRepository.existsByIdUsuarioAndIdFondoAndActivo(any(), any(), any()))
                .thenReturn(false);

        assertThrows(ValidacionException.class,
                () -> service.createAsociacion(asociacion));
    }
    @Test
    void shouldThrowException_whenUsuarioNotFound() {

        AsociacionUsuarioFondo asociacion = new AsociacionUsuarioFondo();
        asociacion.setIdUsuario("1");
        asociacion.setIdFondo("1");
        asociacion.setTipoVinculacion("APERTURA");

        when(fondoRepository.findById("1")).thenReturn(Optional.of(new Fondo()));
        when(usuarioRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> service.createAsociacion(asociacion));
    }

    @Test
    void shouldThrowException_whenCancelWithoutActiveRecord() {

        AsociacionUsuarioFondo asociacion = new AsociacionUsuarioFondo();
        asociacion.setIdUsuario("1");
        asociacion.setIdFondo("1");
        asociacion.setTipoVinculacion("CANCELACION");

        when(fondoRepository.findById("1")).thenReturn(Optional.of(new Fondo()));
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(new Usuario()));

        when(asociacionRepository.findFirstByIdUsuarioAndIdFondoAndActivoOrderByFechaHoraCreacionDesc(
                "1", "1", true)).thenReturn(null);

        when(asociacionRepository.findAllByIdUsuarioOrderByFechaHoraCreacionDesc("1"))
                .thenReturn(java.util.List.of());

        assertThrows(ValidacionException.class,
                () -> service.createAsociacion(asociacion));
    }
}
