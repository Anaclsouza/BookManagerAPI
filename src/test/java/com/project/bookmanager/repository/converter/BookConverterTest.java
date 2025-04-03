package com.project.bookmanager.repository.converter;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookConverterTest {

    @Test
    void testConverterBookEntityToBookDomain(){
        BookEntity bookEntity = new BookEntity(1,"A culpa é das estrelas","autor","romance",2015);

        Book book = BookConverter.converterToDomain(bookEntity);

        assertEquals(bookEntity.getAuthor(), book.getAuthor());
        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getTitle(), book.getTitle());
        assertEquals(bookEntity.getGender(), book.getGender().getValue());
    }

    @Test
    void testConverterBookDomainToBookEntity(){
        Book book = new Book(1,"autor", Gender.FICCAO,2015,"titulo" );

        BookEntity bookEntity = BookConverter.converterToEntity(book);

        assertEquals(bookEntity.getAuthor(), book.getAuthor());
        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getTitle(), book.getTitle());
        assertEquals(bookEntity.getGender(), book.getGender().getValue());

    }
}

