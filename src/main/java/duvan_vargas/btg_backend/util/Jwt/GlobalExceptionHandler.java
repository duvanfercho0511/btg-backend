package duvan_vargas.btg_backend.util.Jwt;

import duvan_vargas.btg_backend.exception.DataNotFoundException;
import duvan_vargas.btg_backend.exception.ValidacionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import duvan_vargas.btg_backend.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Object> handleValidacionException(ValidacionException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), 400));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Recurso no encontrado", 404));
    }
}
