package agoda.homework.exceptions;

public class UnsupportedProtocolException extends Exception {

    public UnsupportedProtocolException() {
        super();
    }

    public UnsupportedProtocolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnsupportedProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedProtocolException(String message) {
        super(message);
    }

    public UnsupportedProtocolException(Throwable cause) {
        super(cause);
    }

}