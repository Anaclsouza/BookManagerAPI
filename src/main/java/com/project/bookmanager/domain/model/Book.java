package com.project.bookmanager.domain.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class Book {

    private Integer id;
    private String author;
    private Gender gender;
    private Integer yearOfPublication;
    private String title;
}
