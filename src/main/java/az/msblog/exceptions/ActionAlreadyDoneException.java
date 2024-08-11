package az.msblog.exceptions;

public class ActionAlreadyDoneException extends RuntimeException {
    public ActionAlreadyDoneException(String message) {
        super(message);
    }
}
