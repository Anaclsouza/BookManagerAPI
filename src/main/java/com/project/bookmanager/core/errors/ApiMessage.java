package com.project.bookmanager.core.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum ApiMessage {

    INTERNAL_ERROR("Internal Error"),
    BOOK_NOT_FOUND("Book not found");


    private final String message;


}
