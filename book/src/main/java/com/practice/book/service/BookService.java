package com.practice.book.service;


import com.practice.book.dto.BookDTO;
import com.practice.book.entity.Book;
import com.practice.book.repository.BookMapper;
import com.practice.book.exceptions.BookNotFoundException;
import com.practice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {


    @Autowired
    private final BookRepository repository;

    @Autowired
    private final BookMapper mapper;



    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public String createBook(BookDTO bookDTO){
        repository.save(mapper.INSTANCE.toBook(bookDTO));
        return "The book was create successfully";
    }

    public List<Book> getBooks(){
        return repository.findAll();
    }

    public Optional<Book>getOneBook(Integer id){
        return Optional.ofNullable(repository.findById(id).
                orElseThrow(() -> new BookNotFoundException(id) {
        }));
    }

    public Optional<Book>getName(String name){
        return Optional.ofNullable(repository.findByName(name));
    }

    public Optional<Book>getWriter(String writer){
        return Optional.ofNullable(repository.findByWriter(writer));
    }

    public Book updateValue(BookDTO newBook, Integer id){
        return repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setWriter(newBook.getWriter());
                    book.setPrice(newBook.getPrice());
                    return repository.save(book);
                }).orElseThrow(()-> new BookNotFoundException(id) {
                });
    }
    public String deelteBook(Integer id){

        if(repository.findById(id).isEmpty()){
            throw new BookNotFoundException(id) {
            };
        }else{
            repository.deleteById(id);
            return "The book was deleted successfully";
        }
    }

    public Book updateByField(Integer id, Map<String, Object> fields){
        Optional<Book> existingBook = repository.findById(id);
        if(existingBook.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Book.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingBook.get(),value);
            });
            return repository.save(existingBook.get());
        }
        return null;
    }

}
