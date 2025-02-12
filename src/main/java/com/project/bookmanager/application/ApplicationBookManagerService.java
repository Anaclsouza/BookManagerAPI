package com.project.bookmanager.application;

import com.project.bookmanager.domain.BookManagerService;
import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Builder

public class ApplicationBookManagerService {

    private final BookManagerService bookManagerService;

    public List<Book> getByQueryParameter(RetrieverBookManager parameter){
        return bookManagerService.bookToGetByParameter(parameter);
    }

    public Book getById(Integer id){
        return bookManagerService.getBookById(id);
    }


    public Book createOrUpdate(Book book){
        return bookManagerService.createOrUpdate(book);
    }

    public void delete(Integer id){
        bookManagerService.delete(id);
    }

    public List<Book> getAllBooks(){
        return bookManagerService.getAllBooks();
    }
}
