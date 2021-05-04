package main.app.exception;

public class ConfigurationException extends AppRunTimeException{
    public ConfigurationException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    public ConfigurationException(String errorCode, String errorMessage, Throwable th) {
        super(errorCode, errorMessage, th);
    }

}

