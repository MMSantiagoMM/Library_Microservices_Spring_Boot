package com.practice.borrowing.exceptions;

public class BorrowingNotFoundException extends RuntimeException{
    public BorrowingNotFoundException(String id){
        super("Borrowing with the id: " + id + "was not found");
    }
}
