package co.edu.uco.noteserviceapi.crosscutting.exception;

public class ResourceNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(final String code, final Object... arguments) {
        super(code, arguments);
    }
}