package com.project.bookmanager.domain;

import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.infra.Impl.BookRepositoryImpl;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import com.project.bookmanager.infra.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Transactional
@Service

public class BookManagerService {

   private final BookRepository bookRepository;
   private final BookRepositoryImpl bookRepositoryImpl;
   private final BookConverter bookConverter;

//    TODO: get que pega o livro pelo id, get que pega o livro por query Param, Create, update e delete
//TODO: tratar erros, excessoes, htpps....
    public Book getBookById(Integer id){
        Optional<BookEntity> bookToGet = bookRepository.findById(id);
        return BookConverter.converterToDomain(bookToGet.get());
    }

    public List<Book> bookToGetByParameter(RetrieverBookManager bookToparams){
         return bookRepositoryImpl.getBookWithQueryParams(bookToparams);
    }

    public Book create(Book book){
        BookEntity bookEntity = bookConverter.converterToEntity(book);
         return BookConverter.converterToDomain(bookRepository.save(bookEntity));
    }
}
