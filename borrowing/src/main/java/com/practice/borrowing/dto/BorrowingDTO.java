package com.practice.borrowing.dto;


import com.practice.borrowing.feign.Book;
import com.practice.borrowing.feign.User;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


@Data
public class BorrowingDTO {


    private Integer user;

    private Integer[] books;

}
