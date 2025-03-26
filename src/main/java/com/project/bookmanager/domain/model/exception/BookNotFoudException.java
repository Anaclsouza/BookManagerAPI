package com.project.bookmanager.domain.model.exception;

import com.project.bookmanager.core.errors.ApiMessage;
import com.project.bookmanager.core.errors.exception.BaseException;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class BookNotFoudException extends BaseException {
        public BookNotFoudException(String message){
            super(message,ApiMessage.BOOK_NOT_FOUND,HttpStatus.NOT_FOUND,Level.DEBUG);
        }
}
