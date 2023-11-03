package com.practice.user.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id){
        super("The user with id: " + id + " was not found");
    }
}
