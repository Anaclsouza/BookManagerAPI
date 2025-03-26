package com.project.bookmanager.domain.model.exception;

import com.project.bookmanager.core.errors.ApiMessage;
import com.project.bookmanager.core.errors.exception.BaseException;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

public class BookRequiresMandatoryFields extends BaseException {
    public BookRequiresMandatoryFields(String message){
        super(message, ApiMessage.BOOK_REQUIRES_MANDATORY_FIELDS, HttpStatus.BAD_REQUEST, Level.DEBUG);
    }
}
