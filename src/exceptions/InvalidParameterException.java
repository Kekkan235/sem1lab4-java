package exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String parameter, String reason) {
        super("Ошибка паараметра " + parameter + ": " + reason);
    }
}
