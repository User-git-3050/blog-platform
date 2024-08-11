package az.msblog.exceptions;

public class SelfUnfollowException extends RuntimeException {
    public SelfUnfollowException(String message) {
        super(message);
    }
}
