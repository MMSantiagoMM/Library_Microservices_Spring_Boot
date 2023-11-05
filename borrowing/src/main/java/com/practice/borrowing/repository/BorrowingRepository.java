package com.practice.borrowing.repository;

import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingRepository extends MongoRepository<Borrowing,Integer> {
}
