package com.project.bookmanager.controller;

import com.project.bookmanager.application.ApplicationBookManagerService;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.exceptions.BookManagerException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class BookManagerControllerImpl {

   private final ApplicationBookManagerService applicationBookManagerService;
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id){
        try {
            Book book = applicationBookManagerService.getById(id);
            return ResponseEntity.ok(book);
        } catch (BookManagerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public List<Book> getBooksByQueryParameter(RetrieverBookManager param){
        return applicationBookManagerService.getByQueryParameter(param);
    }

    @PostMapping
    public ResponseEntity<Book> createOrUpdate(@RequestBody Book book){
        try {
            Book result = applicationBookManagerService.createOrUpdate(book);
            if (book.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (BookManagerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try {
            applicationBookManagerService.delete(id);
             return ResponseEntity.noContent().build();
        } catch (BookManagerException e) {
           return   ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping
    public List<Book> getAllBooks(){
        return applicationBookManagerService.getAllBooks();
    }

}
