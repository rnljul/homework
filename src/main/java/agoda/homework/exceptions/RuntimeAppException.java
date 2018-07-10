package agoda.homework.exceptions;

public class RuntimeAppException extends RuntimeException {

    public RuntimeAppException() {
        super();
    }

    public RuntimeAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RuntimeAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeAppException(String message) {
        super(message);
    }

    public RuntimeAppException(Throwable cause) {
        super(cause);
    }

}
