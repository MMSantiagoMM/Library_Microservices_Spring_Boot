package com.practice.borrowing.controller;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("library")
public class BorrowingController {

    @Autowired
    private BorrowingService service;


    @GetMapping("/get_alls")
    List<Borrowing>getAll(){
        return service.getAllBorrowings();
    }

    @GetMapping("/get_one/{id}")
    Optional<Borrowing> getOne(@PathVariable String id){
        return service.getOne(id);
    }

    @PostMapping("/insert")
    String create(@RequestBody BorrowingDTO borrowingDTO){
        return service.insert(borrowingDTO);
    }

    @PutMapping("/update/{id}")
    String update (@RequestBody BorrowingDTO newBorrowing, @PathVariable String id){
        service.updateBorrowing(newBorrowing,id);
        return "The borrowing was update successfully";
    }


}
