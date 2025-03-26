package com.project.bookmanager.core.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum ApiMessage {

    INTERNAL_ERROR("Internal Error"),
    BOOK_NOT_FOUND("Book not found"),
    BOOK_IS_ALREADY_CREATE("Book is already create"),
    BOOK_REQUIRES_MANDATORY_FIELDS("book requires mandatory fields");


    private final String message;


}
