package duvan_vargas.btg_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2836386312206908528L;
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException() {
        super("Recurso no encontrado");
    }
}

