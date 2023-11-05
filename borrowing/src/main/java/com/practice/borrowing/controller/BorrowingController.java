package com.practice.borrowing.controller;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    Optional<Borrowing> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping("/insert")
    String create(@RequestBody BorrowingDTO borrowingDTO){
        return service.insert(borrowingDTO);
    }

    @PutMapping("/update/{id}")
    String update (@RequestBody BorrowingDTO newBorrowing, @PathVariable Integer id){
        service.updateBorrowing(newBorrowing,id);
        return "The borrowing was update successfully";
    }

    @PatchMapping("/update_field/{id}")
    String updateByField(@PathVariable Integer id, @RequestBody Map<String,Object>fields){
        service.updateByField(id,fields);
        return "The borrowing's field was updated";
    }


}
