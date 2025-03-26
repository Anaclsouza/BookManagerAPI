package com.project.bookmanager.infra.exception;

import com.project.bookmanager.core.errors.ApiMessage;
import com.project.bookmanager.core.errors.exception.BaseException;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class RepositoryException extends BaseException {
    public RepositoryException(String message, Throwable cause){
        super(message, cause, ApiMessage.INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR,Level.ERROR);
    }
}
