package com.practice.borrowing.entity;


import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

@Data
@Document(collection = "borrowings")
public class Borrowing {

    @MongoId
    private String id;

    private User user;

    private ArrayList<Book> books;

    private LocalDate beginingDate;

    private LocalDate endDate;

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(Book ... values) {
        Collections.addAll(books,values);
    }
}
