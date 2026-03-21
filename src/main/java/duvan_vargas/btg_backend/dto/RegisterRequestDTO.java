package duvan_vargas.btg_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO implements Serializable {

    private static final long serialVersionUID = -2432380621627365836L;


    @NotNull(message = "Campo no puede ser nulo")
    private String username;

    @NotNull(message = "Campo no puede ser nulo")
    private String email;

    @NotNull(message = "Campo no puede ser nulo")
    private String password;

    @NotNull(message = "Campo no puede ser nulo")
    private String nombreApellido;

    @NotNull(message = "Campo no puede ser nulo")
    private String numeroTelefono;

}
