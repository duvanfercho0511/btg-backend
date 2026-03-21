package duvan_vargas.btg_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AsociacionUsuarioFondoDTO implements Serializable {

    private static final long serialVersionUID = -467024674629135720L;

    private String id;

    @NotNull(message = "Campo no puede ser nulo")
    private String tipoVinculacion;

    @NotNull(message = "Campo no puede ser nulo")
    private String idUsuario;

    @NotNull(message = "Campo no puede ser nulo")
    private String idFondo;

    private String nombreFondo;

    private Long montoPagado;

    private Date fechaDesde;

    private Date fechaHasta;

    private Boolean activo;

    private Date fechaHoraCreacion;

    @NotNull(message = "Campo no puede ser nulo") //Si es true, se le envía un correo, si es false se le envía un SMS
    private Boolean notifiacionEmail;

}
