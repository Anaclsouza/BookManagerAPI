package com.project.bookmanager.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
