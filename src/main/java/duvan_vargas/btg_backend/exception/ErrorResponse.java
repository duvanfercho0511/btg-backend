package duvan_vargas.btg_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String mensaje;
    private int status;
}