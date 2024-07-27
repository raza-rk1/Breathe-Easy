package breatheasy.airqualityindexservice.exception;

import org.springframework.http.HttpStatus;

public class TokenMandatoryException extends HttpStatusRuntimeException {
    public TokenMandatoryException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
