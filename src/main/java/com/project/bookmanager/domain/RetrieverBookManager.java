package com.project.bookmanager.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.project.bookmanager.domain.model.Gender;
import lombok.*;

@Getter
@Setter
@Builder

public class RetrieverBookManager {
    private String author;
    private String gender;
    private String title;
    private String yearOfPublication;

    private String orderBy;

    private String sort;


}
