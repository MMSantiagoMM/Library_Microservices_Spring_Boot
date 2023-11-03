package com.practice.borrowing.dto;


import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class BorrowingDTO {

    private String id;
    private User user;

    private ArrayList<Book> books;

    private LocalDate beginingDate;

    private LocalDate endDate;

}
