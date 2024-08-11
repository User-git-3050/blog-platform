package az.msblog.exceptions;

public class SelfFollowException extends RuntimeException {
    public SelfFollowException(String message) {
        super(message);
    }
}
