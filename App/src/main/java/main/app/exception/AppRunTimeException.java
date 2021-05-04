package main.app.exception;

import lombok.Getter;

@Getter
public class AppRunTimeException extends RuntimeException{

    private String errorCode;

    public AppRunTimeException(String errorCode, String errorMessage, Throwable th){
        super(errorCode+" : "+errorMessage, th);
        this.errorCode= errorCode;
    }

    AppRunTimeException(String errorCode, String errorMessage){
        super(errorCode+" : "+errorMessage);
        this.errorCode= errorCode;
    }

}
