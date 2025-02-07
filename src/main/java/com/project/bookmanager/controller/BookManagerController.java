package com.project.bookmanager.controller;

import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookManagerController {

   private final BookManagerService bookManagerService;
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id){
        return bookManagerService.getBookById(id);
    }

}
