package exceptions;

public class InvalidFigureRecordException  extends Exception {
    public InvalidFigureRecordException(String message, Throwable cause) {
        super(message,cause);
    }
    public InvalidFigureRecordException(String message) {
        super(message);
    }
}
