package com.project.bookmanager.infra.converter;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.infra.entity.BookEntity;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Builder
@Repository


public class BookConverter {

    public static Book converterToDomain(BookEntity book){
        return Book.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .gender(book.getGender())
                .yearOfPublication(book.getYearOfPublication())
                .build();
    }

    public BookEntity converterToEntity(Book book){
        return new BookEntity(book.getId(),book.getTitle(),book.getAuthor(),book.getGender(),book.getYearOfPublication());
    }
}
