package com.project.bookmanager.infra.Impl;

import com.project.bookmanager.domain.RetrieverBookManager;
import com.project.bookmanager.domain.model.Book;
import com.project.bookmanager.infra.converter.BookConverter;
import com.project.bookmanager.infra.entity.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl {

    private final EntityManager entityManager;

    public List<Book> getBookWithQueryParams(RetrieverBookManager bookParam){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> root = criteriaQuery.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (bookParam.getAuthor() != null){
            predicates.add(criteriaBuilder.equal(root.get("author"),bookParam.getAuthor()));
        }

        if (bookParam.getGender() != null){
            predicates.add(criteriaBuilder.equal(root.get("gender"),bookParam.getGender()));
        }

        if (bookParam.getTitle() != null){
            predicates.add(criteriaBuilder.equal(root.get("title"),bookParam.getTitle()));
        }

        if (bookParam.getYearOfPublication() != null){
            predicates.add(criteriaBuilder.equal(root.get("yearOfPublication"),bookParam.getYearOfPublication()));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
        } else {
            criteriaQuery.select(root);
        }

        if (bookParam.getSort() != null && bookParam.getSort().equals("yearOfPublication")) {
            if (bookParam.getOrderBy().equals("desc")) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("yearOfPublication")));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("yearOfPublication")));

            }
        }


        List<BookEntity> bookEntities = entityManager.createQuery(criteriaQuery).getResultList();

        List<Book> books = new ArrayList<>();

        for (BookEntity bookEntity : bookEntities){
            books.add(BookConverter.converterToDomain(bookEntity));
        }

        return books;

    }


}
