package com.practice.book.service;


import com.practice.book.dto.BookDTO;
import com.practice.book.entity.Book;
import com.practice.book.repository.BookMapper;
import com.practice.book.exceptions.BookNotFoundException;
import com.practice.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.swing.text.html.Option;
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


    public Book createBook(BookDTO bookDTO){
        return repository.save(mapper.INSTANCE.toBook(bookDTO));
    }

    public List<Book> getBooks(){
        return repository.findAll();
    }

    public Optional<Book>getOneBook(Integer id){
        return Optional.ofNullable(repository.findById(id).
                orElseThrow(() -> new BookNotFoundException(id) {
        }));
    }

    public Optional<Book> getByTitle(String name){
        return Optional.ofNullable(repository.findByTitle(name));
    }

    public Optional<Book>getWriter(String writer){
        return Optional.ofNullable(repository.findByWriter(writer));
    }

    public Optional<Book> updateValue(BookDTO newBook, Integer id){
        return Optional.ofNullable(repository.findById(id)
                .map(book -> {
                    book.setTitle(newBook.getTitle());
                    book.setWriter(newBook.getWriter());
                    book.setYear(newBook.getYear());
                    book.setPrice(newBook.getPrice());
                    return repository.save(book);
                }).orElseThrow(() -> new BookNotFoundException(id) {
                }));
    }
    public Boolean deleteBook(Integer id){

        if(repository.findById(id).isEmpty()){
            return false;
        }else{
            repository.deleteById(id);
            return true;
        }
    }

    public Optional<Book> updateByField(Integer id, Map<String, Object> fields){
        Optional<Book> existingBook = repository.findById(id);
        if(existingBook.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Book.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingBook.get(),value);
            });
            return Optional.of(repository.save(existingBook.get()));
        }
        return null;
    }

    public List<Book> returnSeveral(Integer[]values){
        List<Integer> values2 = List.of(values);
        return repository.findAllById(values2);
    }

}
