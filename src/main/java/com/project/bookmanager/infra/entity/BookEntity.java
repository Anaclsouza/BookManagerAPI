package com.project.bookmanager.infra.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "library")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter



public class BookEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "gender")
    private String gender;

    @Column(name = "year_of_publication")
    private Integer yearOfPublication;


}
