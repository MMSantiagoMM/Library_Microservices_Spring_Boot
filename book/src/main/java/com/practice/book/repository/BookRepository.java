package com.practice.book.repository;

import com.practice.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByTitle(String title);

    Book findByWriter(String writer);


}
