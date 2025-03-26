package com.project.bookmanager.core.errors.exception;

import com.project.bookmanager.core.errors.ApiMessage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter

public class BaseException extends RuntimeException{

    private final HttpStatus status;
    private final Level logLevel;
    private final ApiMessage apiMessage;
    private final Map<String, String> metadata;


    //constructor em que voce pode personalizar a mensagem de erro.
    protected BaseException( HttpStatus status, Level logLevel, String message) {
        super(message);
        this.status = status;
        this.logLevel = logLevel;
        this.apiMessage = null;
        this.metadata = null;
    }

    //constructor em que você recebe a mensagem de erro definida na exception que vem de ApiMessage.
    protected BaseException(ApiMessage apiMessage, HttpStatus status, Level logLevel, Map<String, String> metadata){
        super(apiMessage.getMessage());
        this.apiMessage = apiMessage;
        this.status = status;
        this.logLevel = logLevel;
        this.metadata = metadata;
    }

    protected BaseException(String message,ApiMessage apiMessage, HttpStatus status, Level logLevel){
        super(message);
        this.apiMessage = apiMessage;
        this.status = status;
        this.logLevel = logLevel;
        this.metadata = null;
    }


    public BaseException(String message, Throwable cause, ApiMessage apiMessage, HttpStatus httpStatus, Level level) {
        super(message,cause);
        this.apiMessage = apiMessage;
        this.status = httpStatus;
        this.logLevel = level;
        this.metadata = null;


    }
}
