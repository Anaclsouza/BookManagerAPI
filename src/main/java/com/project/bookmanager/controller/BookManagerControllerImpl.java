package com.project.bookmanager.controller;

import com.project.bookmanager.application.ApplicationBookManagerService;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
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
            Book book = applicationBookManagerService.getById(id);
            return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksByQueryParameter(RetrieverBookManager param){
           return ResponseEntity.ok(applicationBookManagerService.getByQueryParameter(param));
    }

    @PostMapping
    public ResponseEntity<Book> createOrUpdate(@RequestBody Book book){
            Book result = applicationBookManagerService.createOrUpdate(book);
            if (book.getId() == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
            applicationBookManagerService.delete(id);
             return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity <List<Book>> getAllBooks() {
           return ResponseEntity.ok(applicationBookManagerService.getAllBooks());
    }

}
