package com.project.bookmanager.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {

    private Integer id;
    private String author;
    private String gender;
    private Integer yearOfPublication;
    private String title;
}
