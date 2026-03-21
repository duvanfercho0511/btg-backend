package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "register_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = -3180914187196092198L;

    private String username;

    private String email;

    private String password;

    private String nombreApellido;

    private String numeroTelefono;
}
