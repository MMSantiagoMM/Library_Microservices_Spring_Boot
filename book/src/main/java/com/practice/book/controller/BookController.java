package com.practice.book.controller;


import com.practice.book.dto.BookDTO;
import com.practice.book.entity.Book;
import com.practice.book.repository.BookRepository;
import com.practice.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private final BookService service;


    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Book> insert(@RequestBody BookDTO book){
        Book createdBook = service.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll(@RequestParam(required = false)String writer,
                                             @RequestParam(required = false)String title){
        List<Book>books = service.getBooks(writer,title);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Book>getOne(@PathVariable Integer id){
        return service.getOneBook(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/get_several/")
    ResponseEntity<List<Book>> getSeveral(@RequestParam List<Integer>values){
        List<Book> books = service.returnSeveral(values);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/maxPrice")
    ResponseEntity<Book> getByPrices(){
        return service.getBookMaxPrice()
                .map(book -> new ResponseEntity<>(book,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @PutMapping("/{id}")
    ResponseEntity<Book>update(@RequestBody BookDTO newBook, @PathVariable Integer id){
        return service.updateValue(newBook, id)
                .map(book -> new ResponseEntity<>(book,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Book> updateByField(@PathVariable Integer id, @RequestBody BookDTO updatedBook){
        return service.updateByField(id,updatedBook)
                .map(field -> new ResponseEntity<>(field,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete (@PathVariable Integer id){
        if(service.deleteBook(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
