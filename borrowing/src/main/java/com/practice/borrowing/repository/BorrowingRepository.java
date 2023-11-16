package com.practice.borrowing.repository;

import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepository extends MongoRepository<Borrowing,Integer> {


    @Query("{'user.name': ?0}")
    List<Borrowing> findByUser(String name);

    @Query("{'books.title': ?0}")
    List<Borrowing> findByBook(String title);

}
