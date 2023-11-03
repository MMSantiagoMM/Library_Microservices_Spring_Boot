package com.practice.borrowing.feign;


import lombok.Data;

@Data
public class Book {

    private String title;
    private String writer;
    private String year;
    private Double price;
}
