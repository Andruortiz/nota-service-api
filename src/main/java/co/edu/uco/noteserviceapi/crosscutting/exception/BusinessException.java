package co.edu.uco.noteserviceapi.crosscutting.exception;


public abstract class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final transient Object[] arguments;

    protected BusinessException(final String code, final Object... arguments) {
        super(code);
        this.code = code;
        this.arguments = arguments;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
