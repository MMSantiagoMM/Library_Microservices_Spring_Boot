package com.practice.book.exceptions;

public abstract class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(Integer id){
        super("The book with the id: " + id + " couldn't be  find");
    }
}
