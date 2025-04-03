package com.project.bookmanager.domain;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import com.project.bookmanager.domain.model.exception.BookNotFoudException;
import com.project.bookmanager.infra.impl.BookRepositoryImpl;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import com.project.bookmanager.infra.repository.BookRepository;
import lombok.Getter;
import lombok.Setter;
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

@Getter
@Setter


@ExtendWith(MockitoExtension.class)
class BookManagerServiceTest {

    @InjectMocks
    BookManagerService bookManagerService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookConverter bookConverter;

    @Mock
    BookRepositoryImpl bookRepositoryImpl;

    RetrieverBookManager retrieverBookManager;

    Book book;
    BookEntity bookEntity;
    BookEntity savedBookEntity;
    Book expectedBook;

    @BeforeEach
    public void setUp() {
        bookEntity = new BookEntity(1, "A pedra filosofal", "JK Rowling", Gender.FICCAO.name(), 1998);
        book = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");
        savedBookEntity = new BookEntity(1, "A pedra filosofal", "JK Rowling", Gender.FICCAO.name(), 1998);
        expectedBook = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");
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
        BookNotFoudException exception = assertThrows(
                BookNotFoudException.class,
                () -> bookManagerService.getBookById(1)
        );
        assertEquals("Book not found with id:1", exception.getMessage());
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
        BookNotFoudException exception = assertThrows(
                BookNotFoudException.class,
                () -> bookManagerService.getAllBooks()
        );

        assertEquals("Books not found", exception.getMessage());
        verify(bookRepository).findAll();
    }

    @Test
    void deleteHappyFlow(){
        when(bookRepository.findById(1)).thenReturn(Optional.of(bookEntity));
        mockConverterToDomain(book);
         bookManagerService.delete(1);
        verify(bookRepository, times(1)).delete(bookEntity);
    }

    @Test
    void deleteWhenBookIsNotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        BookNotFoudException exception = assertThrows(
                BookNotFoudException.class,
                () -> bookManagerService.delete(1)
        );
        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, never()).delete(any());
    }

    @Test
    void updateHappyFlow(){
        book.setAuthor("Ana Clara");
        bookEntity.setAuthor("Ana Clara");
        when(bookRepository.findById(1)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        mockConverterToDomain(book);
        Book updatedBook = bookManagerService.createOrUpdate(book);
        assertNotNull(updatedBook);
        assertEquals("Ana Clara", updatedBook.getAuthor());
        verify(bookRepository).save(any(BookEntity.class));
    }
//    @Test
//    void createHappyFlow() {
//        try (MockedStatic<BookConverter> mockedConverter = mockStatic(BookConverter.class)) {
//            mockedConverter.when(() -> BookConverter.converterToEntity(book)).thenReturn(bookEntity);
//            mockedConverter.when(() -> BookConverter.converterToDomain(savedBookEntity)).thenReturn(expectedBook);
//            when(bookRepository.save(bookEntity)).thenReturn(savedBookEntity);
//
//            Book createdBook = bookManagerService.createOrUpdate(book);
//
//            assertNotNull(createdBook);
//            assertEquals(expectedBook.getId(), createdBook.getId());
//            verify(bookRepository).save(any(BookEntity.class));
//        }
//    }
    @Test
    void updateWhenBookIsNotFound(){
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        BookNotFoudException exception = assertThrows(
                BookNotFoudException.class,
                () -> bookManagerService.getBookById(1)
        );
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, never()).save(any());

    }

    @Test
    void bookToGetByParameterHappyFlow() {
        RetrieverBookManager retriever = RetrieverBookManager.builder()
                .author("JK Rowling")
                .gender("ficcao")
                .build();

        when(bookRepositoryImpl.getBookWithQueryParams(retriever)).thenReturn(Collections.singletonList(book));
        List<Book> result = bookManagerService.bookByParameter(retriever);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book,result.getFirst());
        verify(bookRepositoryImpl, times(1)).getBookWithQueryParams(retriever);
    }
    public static void mockConverterToDomain( Book book) {
        try (MockedStatic<BookConverter> mockedConverter = mockStatic(BookConverter.class)) {
            mockedConverter.when(() -> BookConverter.converterToDomain(any(BookEntity.class)))
                    .thenReturn(book);
        }
    }


}