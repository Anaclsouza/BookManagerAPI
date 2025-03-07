package com.project.bookmanager.application;

import com.project.bookmanager.controller.BookManagerControllerImpl;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationBookManagerServiceTest {

    @InjectMocks
    ApplicationBookManagerService applicationBookManagerService;

    @Mock
    BookManagerService bookManagerService;

    Book mockBook;

    @BeforeEach
    public void setup(){
        mockBook  = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");
    }

    @Test
    void getByQueryParameter(){
        RetrieverBookManager retriever = RetrieverBookManager.builder()
                .author("JK Rowling")
                .gender("ficcao")
                .build();

        when(bookManagerService.bookByParameter(retriever)).thenReturn(Collections.singletonList(mockBook));
        List<Book> book = applicationBookManagerService.getByQueryParameter(retriever);
        assertNotNull(book);
        assertEquals(1, book.size());
        assertTrue(book.contains(mockBook));
    }

    @Test
    void getById(){
        when(bookManagerService.getBookById(mockBook.getId())).thenReturn(mockBook);
        Book book = applicationBookManagerService.getById(mockBook.getId());
        assertNotNull(book);
        assertEquals(mockBook, book);
    }

    @Test
    void update(){
        when(bookManagerService.createOrUpdate(mockBook)).thenReturn(mockBook);

        Book book = applicationBookManagerService.createOrUpdate(mockBook);

        assertNotNull(book);
        assertEquals(mockBook, book);

    }
    @Test
    void create(){
        mockBook.setId(null);
        when(bookManagerService.createOrUpdate(mockBook)).thenReturn(mockBook);

        Book book = applicationBookManagerService.createOrUpdate(mockBook);
        assertNotNull(book);
        assertEquals(mockBook, book);
    }

    @Test
    void delete(){
        applicationBookManagerService.delete(mockBook.getId());

        verify(bookManagerService, times(1)).delete(mockBook.getId());
    }

    @Test
    void getAllBooks(){
     when(bookManagerService.getAllBooks()).thenReturn(Collections.singletonList(mockBook));

     List<Book> book = applicationBookManagerService.getAllBooks();

     assertNotNull(book);
     assertEquals(1, book.size());
    }

}
