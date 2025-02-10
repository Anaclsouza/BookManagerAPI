package com.project.bookmanager.controller;

import com.project.bookmanager.application.ApplicationBookManagerService;
import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookManagerControllerImpl {

   private final ApplicationBookManagerService applicationBookManagerService;
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id){
        return applicationBookManagerService.getById(id);
    }

    @GetMapping
    public List<Book> getBooksByQueryParameter(RetrieverBookManager param){
        return applicationBookManagerService.getByQueryParameter(param);
    }
}
