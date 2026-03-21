package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "password_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {

    @Id
    private String id;

    private String email;

    private String codigo;

    private Date fechaExpiracion;

}
