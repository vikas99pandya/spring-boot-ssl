package main.app.exception;

public class AuthenticatedException extends AppRunTimeException{

    public AuthenticatedException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    public AuthenticatedException(String errorCode, String errorMessage, Throwable th) {
        super(errorCode, errorMessage, th);
    }

}
