package com.project.bookmanager.infra.converter;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import com.project.bookmanager.infra.entity.BookEntity;
import lombok.Builder;
import org.springframework.stereotype.Repository;


@Builder
@Repository


public class BookConverter {

    public static Book converterToDomain(BookEntity book){
        return Book.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .gender(Gender.toEnum(book.getGender()))
                .yearOfPublication(book.getYearOfPublication())
                .build();
    }

    public BookEntity converterToEntity(Book book){
        return new BookEntity(book.getId(),book.getTitle(),book.getAuthor(),book.getGender().getValue(),book.getYearOfPublication());
    }
}
