package com.project.bookmanager.controller;

import com.project.bookmanager.application.ApplicationBookManagerService;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.domain.model.Gender;
import com.project.bookmanager.exceptions.BookManagerException;
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
import static org.mockito.Mockito.when;

@Getter
@Setter

@ExtendWith(MockitoExtension.class)
 class BookmanagerControllerImplTest {

    @InjectMocks
    BookManagerControllerImpl bookmanagerController;

    @Mock
    ApplicationBookManagerService applicationBookManagerService;

    Book mockBook;

    @BeforeEach
    public void setup() {
        mockBook = new Book(1, "JK Rowling", Gender.FICCAO, 1998, "A pedra filosofal");

    }

    @Test
    void getByIdHappyFlow() {
        when(applicationBookManagerService.getById(1)).thenReturn(mockBook);
        ResponseEntity<Book> response = bookmanagerController.getBookById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getById_when_book_is_not_found() {

        when(applicationBookManagerService.getById(2)).thenThrow(new BookManagerException("Book not found"));
        ResponseEntity<Book> response = bookmanagerController.getBookById(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getBooksByQueryParameterHappyFlow() {
        RetrieverBookManager retriever = RetrieverBookManager.builder()
                .author("JK Rowling")
                .gender("ficcao")
                .build();

        when(applicationBookManagerService.getByQueryParameter(retriever)).thenReturn(Collections.singletonList(mockBook));

        ResponseEntity<List<Book>> response = bookmanagerController.getBooksByQueryParameter(retriever);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    void getBooks_when_Book_is_not_Found() {
        RetrieverBookManager retriever = RetrieverBookManager.builder()
                .author("naoExisto")
                .gender("ficcao")
                .build();

        when(applicationBookManagerService.getByQueryParameter(retriever)).thenThrow(new BookManagerException("Book not found"));
        ResponseEntity<List<Book>> response = bookmanagerController.getBooksByQueryParameter(retriever);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    @Test
    void updateHappyFlow(){
        when(applicationBookManagerService.createOrUpdate(mockBook)).thenReturn(mockBook);

        ResponseEntity<Book> response = bookmanagerController.createOrUpdate(mockBook);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    void createHappyFlow(){
        mockBook.setId(null);
        when(applicationBookManagerService.createOrUpdate(mockBook)).thenReturn(mockBook);

        ResponseEntity<Book> response = bookmanagerController.createOrUpdate(mockBook);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    void createError(){
        mockBook.setId(null);
        mockBook.setTitle("LivroJaCadastrado");
        mockBook.setAuthor("AutorJaCadastradoComEsseLIvro");
        when(applicationBookManagerService.createOrUpdate(mockBook)).thenThrow(new BookManagerException("bad request"));

        ResponseEntity<Book> response = bookmanagerController.createOrUpdate(mockBook);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateError(){
        when(applicationBookManagerService.createOrUpdate(mockBook)).thenThrow(new BookManagerException("bad request"));

        ResponseEntity<Book> response = bookmanagerController.createOrUpdate(mockBook);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteHappyFlow(){
        applicationBookManagerService.delete(mockBook.getId());
        ResponseEntity <Void> response = bookmanagerController.delete(mockBook.getId());
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

//    @Test
//    void deleteError(){
//        applicationBookManagerService.delete(1);
//        ResponseEntity <Void> response = bookmanagerController.delete(2);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//    }

@Test
    void getAllBooksHappyFlow(){
        when(applicationBookManagerService.getAllBooks()).thenReturn(Collections.singletonList(mockBook));

        ResponseEntity <List<Book>> response = bookmanagerController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
}

@Test
    void getAllBooksError(){
        when(applicationBookManagerService.getAllBooks()).thenThrow(new BookManagerException("Book is not found"));

        ResponseEntity <List<Book>> response = bookmanagerController.getAllBooks();

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}


}
