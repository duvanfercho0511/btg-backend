package duvan_vargas.btg_backend.dto;

import duvan_vargas.btg_backend.model.Rol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = -1243383114432572869L;

    private String id;

    private String username;
    private String password;
    private String email;

    private String nombreApellido;

    private String numeroTelefono;

    private Long montoInicial;

    private Rol rol;

    private Date fechaHoraCreacion;

}
