package com.practice.borrowing.feign;


import lombok.Data;

@Data
public class User {

    private Integer id;
    private String name;
    private String document;
    private String phoneNumber;
}
