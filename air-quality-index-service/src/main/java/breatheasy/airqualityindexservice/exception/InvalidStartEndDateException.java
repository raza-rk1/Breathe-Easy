package breatheasy.airqualityindexservice.exception;

public class InvalidStartEndDateException extends RuntimeException {
    public InvalidStartEndDateException(String errorMessage) {
        super(errorMessage);
    }
}
