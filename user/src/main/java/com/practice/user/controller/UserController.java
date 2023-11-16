package com.practice.user.controller;


import com.practice.user.dto.UserDTO;
import com.practice.user.entity.User;
import com.practice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;



    @GetMapping("/{id}")
    ResponseEntity<User> getOne(@PathVariable Integer id){
        return service.getOne(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    ResponseEntity<List<User>> getAll(@RequestParam(required = false)String name){
        List<User> users = service.getAll(name);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User newUser = service.insert(userDTO);
        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<User>update(@PathVariable Integer id, @RequestBody UserDTO newUser){
        return service.update(newUser,id)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    ResponseEntity<User>updateByField(@PathVariable Integer id,
                                      @RequestBody UserDTO updatedUser){
        return service.updateByField(id,updatedUser)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        if(service.deleteUser(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
