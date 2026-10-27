package co.edu.uco.noteserviceapi.infrastructure.input.handler;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessException;
import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ConflictException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final ResourceNotFoundException exception) {
        return build(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinessRule(final BusinessRuleException exception) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, exception);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(final ConflictException exception) {
        return build(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(final AccessDeniedException exception) {
        return build(HttpStatus.FORBIDDEN, MessageCatalog.ACCESS_DENIED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(final MethodArgumentNotValidException exception) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, MessageCatalog.INVALID_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(final HttpMessageNotReadableException exception) {
        return build(HttpStatus.BAD_REQUEST, MessageCatalog.INVALID_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(final Exception exception) {
        LOGGER.error(MessageCatalog.UNEXPECTED_ERROR.getTechnicalMessage(), exception);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, MessageCatalog.UNEXPECTED_ERROR);
    }

    private ResponseEntity<ErrorResponse> build(final HttpStatus status, final BusinessException exception) {
        LOGGER.warn("{}: {}", status.value(), exception.getCode());
        return ResponseEntity.status(status).body(toResponse(exception.getCode()));
    }

    private ResponseEntity<ErrorResponse> build(final HttpStatus status, final MessageCatalog message) {
        return ResponseEntity.status(status).body(toResponse(message.name()));
    }

    private ErrorResponse toResponse(final String code) {
        try {
            return new ErrorResponse(code, MessageCatalog.valueOf(code).getUserMessage());
        } catch (IllegalArgumentException unknownCode) {
            return new ErrorResponse(code, MessageCatalog.INVALID_REQUEST.getUserMessage());
        }
    }
}
