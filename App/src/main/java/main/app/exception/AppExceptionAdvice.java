package main.app.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
@Slf4j
@RestControllerAdvice
public class AppExceptionAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex, WebRequest request) {
        return populateErrorDetails(ex,"run time error",HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AuthenticatedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponse> handleConflict(
            AuthenticatedException ex, WebRequest request) {
        return populateErrorDetails(ex,ex.getErrorCode(),HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorResponse> populateErrorDetails(Exception ex, String errorCode, HttpStatus status){
        log.error("Exception: ",ex.getMessage());
        return new ResponseEntity<> (ErrorResponse.builder().errorCode(errorCode)
                .status(status.value())
                .errorMessage(ex.getMessage()).build(), status);
    }
}
