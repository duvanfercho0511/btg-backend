package duvan_vargas.btg_backend.service.impl;

import duvan_vargas.btg_backend.exception.DataNotFoundException;
import duvan_vargas.btg_backend.exception.ValidacionException;
import duvan_vargas.btg_backend.model.AsociacionUsuarioFondo;
import duvan_vargas.btg_backend.model.Fondo;
import duvan_vargas.btg_backend.model.Usuario;
import duvan_vargas.btg_backend.repository.AsociacionUsuarioFondoRepository;
import duvan_vargas.btg_backend.repository.FondoRepository;
import duvan_vargas.btg_backend.repository.UsuarioRepository;
import duvan_vargas.btg_backend.service.interfaces.IAsociacionUsuarioFondoService;
import duvan_vargas.btg_backend.util.IConstantes;
import duvan_vargas.btg_backend.util.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsociacionUsuarioFondoServiceImpl implements IAsociacionUsuarioFondoService {

    private AsociacionUsuarioFondoRepository asociacionUsuarioFondoRepository;

    private UsuarioRepository usuarioRepository;

    private FondoRepository fondoRepository;

    private final SmsService smsService;

    private final JavaMailSender javaMailSender;

    @Override
    public List<AsociacionUsuarioFondo> findAllAsociacionByIdUsuario(String idUsuario) {
        return this.asociacionUsuarioFondoRepository.findAllByIdUsuarioOrderByFechaHoraCreacionDesc(idUsuario);
    }

    @Override
    @Transactional
    public AsociacionUsuarioFondo createAsociacion(AsociacionUsuarioFondo asociacionUsuarioFondo) {

        Date fechaActual = new Date();
        Fondo fondo = this.fondoRepository.findById(asociacionUsuarioFondo.getIdFondo()).orElseThrow(DataNotFoundException::new);
        Usuario usuario = this.usuarioRepository.findById(asociacionUsuarioFondo.getIdUsuario()).orElseThrow(DataNotFoundException::new);

        if(asociacionUsuarioFondo.getTipoVinculacion().equals(IConstantes.NOMBRE_TIPO_VINCULACION_APERTURA)){
            this.validarApertura(asociacionUsuarioFondo);

            //Se valida el monto minimo directamente
            if(fondo.getMontoMinimo() > usuario.getMontoInicial()) throw new ValidacionException("No tiene saldo disponible para vincularse al fondo " + fondo.getNombre());

            //Se setea la asociacion
            asociacionUsuarioFondo.setFechaDesde(fechaActual);
            asociacionUsuarioFondo.setFechaHoraCreacion(fechaActual);
            asociacionUsuarioFondo.setActivo(true);
            asociacionUsuarioFondo.setNombreFondo(fondo.getNombre());
            asociacionUsuarioFondo.setMontoPagado(fondo.getMontoMinimo());
            AsociacionUsuarioFondo asociacionUsuarioFondoBD = this.asociacionUsuarioFondoRepository.save(asociacionUsuarioFondo);

            //Se setea el fondo
            fondo.setValorTotalVinculaciones(fondo.getValorTotalVinculaciones() + fondo.getMontoMinimo());
            this.fondoRepository.save(fondo);

            //Se setea el usuario

            usuario.setMontoInicial(usuario.getMontoInicial() - fondo.getMontoMinimo());
            this.usuarioRepository.save(usuario);

            if(Boolean.TRUE.equals(asociacionUsuarioFondo.getNotifiacionEmail())){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(usuario.getEmail());
                message.setSubject("Suscripcion exitosa a fondo");
                message.setText("Tu suscripcion fue exitosa al fondo: " + fondo.getNombre() );
                javaMailSender.send(message);
            }else{
                //Este metodo de mensajería solo funciona con números confirmados, es funcional,
                //de cualquier manera el envío de email siempre está disponible
                this.smsService.enviarSms(
                        usuario.getNumeroTelefono(),
                        "Tu suscripcion fue exitosa al fondo: " + fondo.getNombre()
                );
            }

            return asociacionUsuarioFondoBD;

        }else{
            this.validarCancelacion(asociacionUsuarioFondo);
            AsociacionUsuarioFondo asociacionUsuarioFondoVigente = this.asociacionUsuarioFondoRepository.findFirstByIdUsuarioAndIdFondoAndActivoOrderByFechaHoraCreacionDesc(asociacionUsuarioFondo.getIdUsuario(), asociacionUsuarioFondo.getIdFondo(), true);

            //Validacion por si tratan de crear una cancelación cuando la tabla está vacía
            if(asociacionUsuarioFondoVigente == null) throw new ValidacionException("No hay registros disponibles para cancelar para el mismo usuario y fondo.");

            asociacionUsuarioFondoVigente.setFechaHasta(fechaActual);
            asociacionUsuarioFondoVigente.setActivo(false);

            this.asociacionUsuarioFondoRepository.save(asociacionUsuarioFondoVigente);

            //Se setea la asociacion
            asociacionUsuarioFondo.setFechaDesde(fechaActual);
            asociacionUsuarioFondo.setFechaHasta(fechaActual);
            asociacionUsuarioFondo.setFechaHoraCreacion(fechaActual);
            asociacionUsuarioFondo.setActivo(false);
            asociacionUsuarioFondo.setNombreFondo(fondo.getNombre());
            asociacionUsuarioFondo.setMontoPagado( - fondo.getMontoMinimo());
            AsociacionUsuarioFondo asociacionUsuarioFondoBD = this.asociacionUsuarioFondoRepository.save(asociacionUsuarioFondo);

            //Se setea el fondo
            fondo.setValorTotalVinculaciones(fondo.getValorTotalVinculaciones() - fondo.getMontoMinimo());
            this.fondoRepository.save(fondo);

            //Se setea el usuario

            usuario.setMontoInicial(usuario.getMontoInicial() + fondo.getMontoMinimo());
            this.usuarioRepository.save(usuario);

            return asociacionUsuarioFondoBD;
        }
    }

    private void validarApertura(AsociacionUsuarioFondo asociacionUsuarioFondo){
        var exists = this.asociacionUsuarioFondoRepository.existsByIdUsuarioAndIdFondoAndActivo(asociacionUsuarioFondo.getIdUsuario(), asociacionUsuarioFondo.getIdFondo(), true);
        if(Boolean.TRUE.equals(exists)) throw new ValidacionException("Ya existe una asociacion de apertura para el mismo usuario y fondo.");
    }

    private void validarCancelacion(AsociacionUsuarioFondo asociacionUsuarioFondo){

        var historialCancelacion = this.asociacionUsuarioFondoRepository.findAllByIdUsuarioOrderByFechaHoraCreacionDesc(asociacionUsuarioFondo.getIdUsuario());

        //Acá se valida en el historial asociaciones, si hay un registro activo para un fondo para cancelarlo, si se deja solo la validación de abajo, cuando hay varias asociaciones para un mismo usuario y fondo, falla
        var ultimo = historialCancelacion.stream()
                .filter(a -> a.getIdFondo().equals(asociacionUsuarioFondo.getIdFondo()))
                .filter(a -> "APERTURA".equals(a.getTipoVinculacion()))
                .filter(a -> Boolean.FALSE.equals(a.getActivo()))
                .max(Comparator.comparing(AsociacionUsuarioFondo::getFechaHoraCreacion))
                .orElse(null);

        if(ultimo == null){
            //Acá se valida que haya un registro que esté activo el cual vaya a ser cancelado
            var exists = this.asociacionUsuarioFondoRepository.existsByIdUsuarioAndIdFondoAndActivo(asociacionUsuarioFondo.getIdUsuario(), asociacionUsuarioFondo.getIdFondo(), false);
            if(Boolean.TRUE.equals(exists)) throw new ValidacionException("No hay registros disponibles para cancelar para el mismo usuario y fondo.");
        }

    }

    @Autowired
    public void setAsociacionUsuarioFondoRepository(AsociacionUsuarioFondoRepository asociacionUsuarioFondoRepository) {
        this.asociacionUsuarioFondoRepository = asociacionUsuarioFondoRepository;
    }

    @Autowired
    public void setFondoRepository(FondoRepository fondoRepository) {
        this.fondoRepository = fondoRepository;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
