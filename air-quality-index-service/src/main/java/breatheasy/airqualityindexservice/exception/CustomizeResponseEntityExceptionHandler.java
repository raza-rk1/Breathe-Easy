package breatheasy.airqualityindexservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCoordinateNotFoundException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.name()).message(exception.getMessage()).path(request.getDescription(false)).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(InvalidStartEndDateException.class)
    public ResponseEntity<ErrorDetails> handleInvalidStartEndDateException(Exception exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.name()).message(exception.getMessage()).path(request.getDescription(false)).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(LocalDateTime.now()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.name()).message(ex.getMessage()).path(request.getDescription(false)).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.name())
                .message(ex.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", ")))
                .path(request.getDescription(false)).build();

        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

}
