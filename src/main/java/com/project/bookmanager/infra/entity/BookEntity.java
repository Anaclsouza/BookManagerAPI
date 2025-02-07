package com.project.bookmanager.infra.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "library")
@RequiredArgsConstructor
@Getter
@Setter
@Repository

public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "gender")
    private String gender;

    @Column(name = "year_of_publication")
    private Integer yearOfPublication;



}
