package duvan_vargas.btg_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = -5945021442138206744L;


    @NotNull(message = "Campo no puede ser nulo")
    private String username;

    @NotNull(message = "Campo no puede ser nulo")
    private String password;
}
