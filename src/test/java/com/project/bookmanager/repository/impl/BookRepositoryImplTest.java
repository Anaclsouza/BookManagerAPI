package com.project.bookmanager.repository.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.infra.entity.BookEntity;
import com.project.bookmanager.infra.impl.BookRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<BookEntity> criteriaQuery;

    @Mock
    private Root<BookEntity> root;

    @Mock
    private TypedQuery<BookEntity> typedQuery;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    private BookEntity bookEntity;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity(1, "Book Title", "Author Name", "romance", 2020);
    }

    @Test
    void testGetBookWithQueryParams() {
        RetrieverBookManager retrieverBookManager = RetrieverBookManager.builder()
                .author("Author Name")
                .gender("romance")
                .build();

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(BookEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(BookEntity.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(bookEntity));

        List<Book> books = bookRepository.getBookWithQueryParams(retrieverBookManager);

        assertEquals(1, books.size());
        assertEquals("Book Title", books.get(0).getTitle());
        verify(entityManager).getCriteriaBuilder();
        verify(entityManager).createQuery(criteriaQuery);
    }
}