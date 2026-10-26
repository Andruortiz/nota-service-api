package co.edu.uco.noteserviceapi.crosscutting.exception;

public class BusinessRuleException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public BusinessRuleException(final String code, final Object... arguments) {
        super(code, arguments);
    }
}
