package com.project.bookmanager.infra.converter;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.infra.entity.BookEntity;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository


public class BookConverter {

    public Book converterToDomain(BookEntity book){
        return new Book(book.getId(), book.getAuthor(), book.getGender(), book.getYearOfPublication(),book.getTitle());
    }
}
