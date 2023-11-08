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

    @Autowired
    private final BookRepository repository;




    public BookController(BookService service, BookRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/create")
    public ResponseEntity<Book> insert(@RequestBody BookDTO book){
        Book createdBook = service.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping("/get_books")
    public ResponseEntity<List<Book>> getAll(){
        List<Book> books = service.getBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }

    @GetMapping("/get_one/{id}")
    ResponseEntity<Book>getOne(@PathVariable Integer id){
        return service.getOneBook(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{title}")
    ResponseEntity<Book>getName(@PathVariable String title){
        return service.getByTitle(title)
                .map(name -> new ResponseEntity<>(name,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/writer/{writer}")
    ResponseEntity<Book>getWriter(@PathVariable String writer){
        return service.getWriter(writer)
                .map(name -> new ResponseEntity<>(name,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get_several/{values}")
    ResponseEntity<List<Book>> getSeveral(@PathVariable Integer[] values){
        List<Book> books = service.returnSeveral(values);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    ResponseEntity<Book>update(@RequestBody BookDTO newBook, @PathVariable Integer id){
        return service.updateValue(newBook, id)
                .map(book -> new ResponseEntity<>(book,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/update_field/{id}")
    ResponseEntity<Book> updateByField(@PathVariable Integer id, @RequestBody Map<String, Object> fields){
        return service.updateByField(id,fields)
                .map(field -> new ResponseEntity<>(field,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<Void> delete (@PathVariable Integer id){
        if(service.deleteBook(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
