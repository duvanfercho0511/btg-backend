package duvan_vargas.btg_backend.exception;


public class ValidacionException extends RuntimeException {
    private static final long serialVersionUID = 3053566579779021848L;

    public ValidacionException() {
        super("Validacion no existosa");
    }

    public ValidacionException(String aMensaje) {
        super(aMensaje);
    }
}