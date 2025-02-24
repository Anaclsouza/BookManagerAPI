package com.project.bookmanager.domain;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import com.project.bookmanager.exceptions.BookManagerException;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import com.project.bookmanager.infra.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookManagerServiceTest {

    @InjectMocks
    BookManagerService bookManagerService;

    @Mock
    BookRepository bookRepository;

    Book book;
    BookEntity bookEntity;

    @BeforeEach
    public void setUp() {
        bookEntity = new BookEntity(1, "A pedra filosofal", "JK Rowling", Gender.FICCAO.name(), 1998);
        book = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");
    }

    @Test
    void getByIdHappyFlow() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(bookEntity));
            mockConverterToDomain(book);
            Book responseBook = bookManagerService.getBookById(1);

            assertNotNull(responseBook);
            assertEquals(1, book.getId());
            assertEquals("JK Rowling", book.getAuthor());
            assertEquals("A pedra filosofal", book.getTitle());
            assertEquals(1998, book.getYearOfPublication());

            verify(bookRepository).findById(1);
    }

    @Test
    void getByIdWhenIdNotFound(){
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookManagerException exception = assertThrows(
                BookManagerException.class,
                () -> bookManagerService.getBookById(1)
        );

        assertEquals("Book not found with id: 1", exception.getMessage());

        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void getByAllHappyFlow(){
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookEntity));

        mockConverterToDomain(book);

       List<Book> responseBook = bookManagerService.getAllBooks();

       assertNotNull(responseBook);
       assertFalse(responseBook.isEmpty());

       verify(bookRepository).findAll();
    }

    @Test
    void getByAllWhenBookIsNotFound(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        List <Book> responseBook = bookManagerService.getAllBooks();

        assertNotNull(responseBook);
        assertTrue(responseBook.isEmpty());
        assertEquals(0,responseBook.size());


        verify(bookRepository).findAll();
    }

//    TODO: criar tratamento de erro para converter
//    @Test
//    void getByAllWhenConverterFailed(){
//        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookEntity));
//
//        try (MockedStatic<BookConverter> mockedConverter = mockStatic(BookConverter.class)) {
//            mockedConverter.when(() -> BookConverter.converterToDomain(any(BookEntity.class)))
//                    .thenReturn(book).;
//        }
//
//    }

    public static void mockConverterToDomain( Book book) {
        try (MockedStatic<BookConverter> mockedConverter = mockStatic(BookConverter.class)) {
            mockedConverter.when(() -> BookConverter.converterToDomain(any(BookEntity.class)))
                    .thenReturn(book);
        }
    }

}