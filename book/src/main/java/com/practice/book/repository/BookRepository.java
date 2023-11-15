package com.practice.book.repository;

import com.practice.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {


    @Query(value = "SELECT * FROM books WHERE price = (SELECT MAX (price) FROM books)"
            ,nativeQuery = true)
    Book getBookWithMaxPrice();

    @Query(value = "SELECT * FROM books b WHERE upper(b.writer) LIKE upper(" +
            "concat('%', ?1, '%'))", nativeQuery = true)
    List<Book> getByWriter(String writer);


    @Query(value = "FROM Book b WHERE upper(b.title) LIKE upper (concat('%', :fullTitle, '%'))")
    List<Book> getByTitle(@Param("fullTitle") String fullTitle);

}
