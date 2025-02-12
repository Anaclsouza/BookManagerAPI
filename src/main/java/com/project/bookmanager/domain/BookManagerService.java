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

//TODO: get que pega o livro pelo id, get que pega o livro por query Param, Create, update e delete - OK
//TODO: tratar erros, excessoes, htpps....
//TODO: transferir a logica para application?
//TODO: criar um helper para adicionar ao create pra evitar que ele crie livros repetidos - OK
//TODO:criar um get que retorna todos os registros? - OK
//TODO:criar enum para generos?
//TODO: melhorar URls


    public Book getBookById(Integer id){
        Optional<BookEntity> bookToGet = bookRepository.findById(id);
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
        bookToUpdate.get().setAuthor(book.getAuthor());
        bookToUpdate.get().setGender(book.getGender());
        bookToUpdate.get().setTitle(book.getTitle());
        bookToUpdate.get().setYearOfPublication(book.getYearOfPublication());

        return BookConverter.converterToDomain(bookRepository.save(bookToUpdate.get()));
        }
        checkTitleAndAuthor(book);
        BookEntity bookToCreate = bookConverter.converterToEntity(book);
        return BookConverter.converterToDomain(bookRepository.save(bookToCreate));

    }

    public void delete(Integer id){
        BookEntity bookToDelete = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("book is not found"));

        bookRepository.delete(bookToDelete);

    }

    public void checkTitleAndAuthor(Book book) {
        if (book.getTitle() != null && book.getAuthor() != null) {
            if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
                throw new RuntimeException("The book is already created");
            }
        }
    }
}
