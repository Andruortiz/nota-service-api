package co.edu.uco.noteserviceapi.crosscutting.exception;

public class ConflictException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ConflictException(final String code, final Object... arguments) {
        super(code, arguments);
    }
}