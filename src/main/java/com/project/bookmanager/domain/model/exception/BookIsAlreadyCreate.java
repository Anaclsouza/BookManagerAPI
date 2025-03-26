package com.project.bookmanager.domain.model.exception;

import com.project.bookmanager.core.errors.ApiMessage;
import com.project.bookmanager.core.errors.exception.BaseException;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public class BookIsAlreadyCreate extends BaseException {
    public BookIsAlreadyCreate(String message){
        super(message, ApiMessage.BOOK_IS_ALREADY_CREATE, HttpStatus.BAD_REQUEST, Level.DEBUG);
    }
}
