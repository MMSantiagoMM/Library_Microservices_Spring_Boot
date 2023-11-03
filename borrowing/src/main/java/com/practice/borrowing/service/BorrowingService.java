package com.practice.borrowing.service;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.feign.BookFeign;
import com.practice.borrowing.feign.UserFeign;
import com.practice.borrowing.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository repository;

    @Autowired
    private BookFeign bookFeign;

    @Autowired
    private UserFeign userFeign;




}
