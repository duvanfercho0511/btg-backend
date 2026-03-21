package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "login_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 4545569535034967314L;

    private String username;
    private String password;
}
