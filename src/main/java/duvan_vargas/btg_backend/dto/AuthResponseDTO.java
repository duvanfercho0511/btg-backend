package duvan_vargas.btg_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDTO implements Serializable {

    private static final long serialVersionUID = -289134949499480272L;

    String accesToken;
    String refreshToken;

}
