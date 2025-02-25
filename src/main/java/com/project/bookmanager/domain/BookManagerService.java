package com.project.bookmanager.domain;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.exceptions.BookManagerException;
import com.project.bookmanager.infra.Impl.BookRepositoryImpl;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import com.project.bookmanager.infra.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Transactional
@Service

public class BookManagerService {

   private final BookRepository bookRepository;
   private final BookRepositoryImpl bookRepositoryImpl;
   private final BookConverter bookConverter;

    public Book getBookById(Integer id){
        Optional<BookEntity> bookToGet = bookRepository.findById(id);
        if (bookToGet.isEmpty()){
            throw new BookManagerException("Book not found with id: " +id);
        }
        return BookConverter.converterToDomain(bookToGet.get());
    }

    public List<Book> bookToGetByParameter(RetrieverBookManager bookToparams){
         return bookRepositoryImpl.getBookWithQueryParams(bookToparams);
    }

    public List<Book> getAllBooks(){
          List<BookEntity> bookToGet = bookRepository.findAll();
          List<Book> books = new ArrayList<>();

        for (BookEntity bookEntity : bookToGet){
            books.add(BookConverter.converterToDomain(bookEntity));
        }
        return books;
    }
    public Book createOrUpdate(Book book){
        if (book.getId() != null){
        Optional<BookEntity> bookToUpdate = bookRepository.findById(book.getId());
        if (bookToUpdate.isEmpty()){
            throw new BookManagerException("book is not found");
        }
       updateBookEntity(bookToUpdate.get(),book);
        return BookConverter.converterToDomain(bookRepository.save(bookToUpdate.get()));
        }
        checkMandatoryParamsToCreate(book);
        checkTitleAndAuthor(book);
        BookEntity bookToCreate = bookConverter.converterToEntity(book);
        return BookConverter.converterToDomain(bookRepository.save(bookToCreate));
    }
    private void updateBookEntity(BookEntity bookEntity, Book book) {
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setGender(book.getGender().toString());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setYearOfPublication(book.getYearOfPublication());
    }

    public void delete(Integer id){
        BookEntity bookToDelete = bookRepository.findById(id)
                .orElseThrow(() -> new BookManagerException("book is not found"));

        bookRepository.delete(bookToDelete);

    }

    //helpers:

    public void checkTitleAndAuthor(Book book) {
        if (book.getTitle() != null && book.getAuthor() != null) {
            if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
                throw new BookManagerException("The book is already created");
            }
        }
    }

    public void checkMandatoryParamsToCreate(Book book){
        if(book.getTitle() == null){
            throw new BookManagerException("The parameter 'title' is mandatory");
        }
        if(book.getAuthor() == null){
            throw new BookManagerException("The parameter 'author' is mandatory");
        }
        if(book.getGender() == null){
            throw new BookManagerException("the parameter 'gender' is mandatory");
        }
    }
}
