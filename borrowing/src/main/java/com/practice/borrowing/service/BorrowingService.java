package com.practice.borrowing.service;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.exceptions.BorrowingNotFoundException;
import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.BookFeign;
import com.practice.borrowing.feign.UserFeign;
import com.practice.borrowing.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository repository;

    @Autowired
    private BookFeign bookFeign;

    @Autowired
    private UserFeign userFeign;


    public List<Borrowing> getAllBorrowings(){
        return repository.findAll();
    }

    public Optional<Borrowing> getOne(String id){
        return Optional.of(repository.findById(id)).orElseThrow(
                ()->new BorrowingNotFoundException(id));
    }

    public String insert(BorrowingDTO borrowingDTO){
        Borrowing borrowing = new Borrowing();
        borrowing.setId(borrowingDTO.getId());
        borrowing.setUser(userFeign.getOne(borrowingDTO.getUser()));
        borrowing.setBooks(bookFeign.getSeveral(borrowingDTO.getBooks()));
        borrowing.setBeginingDate(LocalDate.now());
        borrowing.setEndDate(borrowing.getBeginingDate().plusDays(30));
        borrowing.setTotalPrice(calculateTotalPrice(borrowing));
        repository.save(borrowing);
        return "The borrowing was created successfully";
    }

    public Double calculateTotalPrice(Borrowing borrowing){
        return borrowing.getBooks().stream().mapToDouble(
                Book::getPrice).sum();
    }

    public Optional<Borrowing> updateBorrowing(BorrowingDTO borrowingDTO, String id){
        return Optional.ofNullable(repository.findById(id)
                .map(borrowing -> {
                    borrowing.setId(borrowingDTO.getId());
                    borrowing.setUser(userFeign.getOne(borrowingDTO.getUser()));
                    borrowing.setBooks(bookFeign.getSeveral(borrowingDTO.getBooks()));
                    borrowing.setBeginingDate(LocalDate.now());
                    borrowing.setEndDate(borrowing.getBeginingDate().plusDays(30));
                    borrowing.setTotalPrice(calculateTotalPrice(borrowing));
                    return repository.save(borrowing);
                }).orElseThrow(() -> new BorrowingNotFoundException(id)));
    }




}