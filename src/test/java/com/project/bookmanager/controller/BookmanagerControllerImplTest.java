package com.project.bookmanager.controller;

import com.project.bookmanager.application.ApplicationBookManagerService;
import com.project.bookmanager.core.errors.exception.BaseException;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Getter
@Setter

@ExtendWith(MockitoExtension.class)
 class BookmanagerControllerImplTest {

    @InjectMocks
    BookManagerControllerImpl bookManagerController;
    @Mock
    ApplicationBookManagerService applicationBookManagerService;

    Book book;

    @BeforeEach
    public void setup() {
        book = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");

    }

    @Test
    void getByQueryParameter(){
        RetrieverBookManager retriever = RetrieverBookManager.builder()
                .author("JK Rowling")
                .gender("ficcao")
                .build();

        when(applicationBookManagerService.getByQueryParameter(retriever)).thenReturn(Collections.singletonList(book));
        ResponseEntity<List<Book>> response = bookManagerController.getBooksByQueryParameter(retriever);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetBookById() {
        when(applicationBookManagerService.getById(1)).thenReturn(book);
        ResponseEntity<Book> response = bookManagerController.getBookById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void update(){

        when(applicationBookManagerService.createOrUpdate(book)).thenReturn(book);

        ResponseEntity<Book> response = bookManagerController.createOrUpdate(book);

        assertNotNull(book);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    void create(){
        book.setId(null);
        when(applicationBookManagerService.createOrUpdate(book)).thenReturn(book);

        ResponseEntity<Book> response = bookManagerController.createOrUpdate(book);

        assertNotNull(book);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    void delete(){
        bookManagerController.delete(book.getId());

        verify(applicationBookManagerService).delete(book.getId());
    }

    @Test
    void getAllBooks(){
        when(applicationBookManagerService.getAllBooks()).thenReturn(Collections.singletonList(book));

        ResponseEntity<List<Book>> response = bookManagerController.getAllBooks();

        assertNotNull(book);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
