package main.app.exception;

public class ServiceRaceException  extends AppRunTimeException{

    public ServiceRaceException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    public ServiceRaceException(String errorCode, String errorMessage, Throwable th) {
        super(errorCode, errorMessage, th);
    }

}