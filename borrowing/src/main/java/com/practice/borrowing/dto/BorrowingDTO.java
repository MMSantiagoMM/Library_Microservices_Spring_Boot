package com.practice.borrowing.dto;


import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


@Data
public class BorrowingDTO {

    private String id;

    private Integer user;

    private ArrayList<Integer> books;

    public ArrayList<Integer> getBooks() {
        return books;
    }

    public void setBooks(Integer ... values) {
        Collections.addAll(books,values);
    }
}
