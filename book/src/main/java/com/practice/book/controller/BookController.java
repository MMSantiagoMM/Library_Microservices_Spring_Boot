package com.practice.book.controller;


import com.practice.book.dto.BookDTO;
import com.practice.book.entity.Book;
import com.practice.book.exceptions.BookNotFoundException;
import com.practice.book.repository.BookRepository;
import com.practice.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    private final BookService service;

    @Autowired
    private final BookRepository repository;




    public BookController(BookService service, BookRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/create")
    public String insert(@RequestBody BookDTO book){
        return service.createBook(book);
    }

    @GetMapping("/get_books")
    public List<Book> getAll(){
        return service.getBooks();
    }

    @GetMapping("/get_one/{id}")
    Optional<Book>getOne(@PathVariable Integer id){
        return service.getOneBook(id);
    }

    @GetMapping("/name/{name}")
    Optional<Book>getName(@PathVariable String name){
        return service.getName(name);
    }

    @GetMapping("writer/{writer}")
    Optional<Book>getWriter(@PathVariable String writer){
        return service.getWriter(writer);
    }

    @PutMapping("/update/{id}")
    Optional<Book>update(@RequestBody BookDTO newBook, @PathVariable Integer id){
        return Optional.ofNullable(service.updateValue(newBook, id));
    }

    @PatchMapping("/update_field/{id}")
    Book updateByField(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        return service.updateByField(id,fields);
    }

    @DeleteMapping("delete/{id}")
    String delete (@PathVariable Integer id){
        return service.deelteBook(id);
    }

}
