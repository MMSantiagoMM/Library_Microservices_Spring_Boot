package com.practice.borrowing.entity;


import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Document(collection = "borrowings")
public class Borrowing {

    @Transient
    public static final String SEQUENCE_NAME="user_sequence";

    @Id
    private Integer id;

    private User user;

    private List<Book> books;

    private LocalDate beginingDate;

    private LocalDate endDate;

    private Double totalPrice;

}
