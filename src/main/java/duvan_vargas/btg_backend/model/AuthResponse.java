package duvan_vargas.btg_backend.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth_response")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String accesToken;
    private String refreshToken;
}
