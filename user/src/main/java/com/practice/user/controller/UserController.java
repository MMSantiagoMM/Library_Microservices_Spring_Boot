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

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getOne(@PathVariable Integer id){
        return service.getOne(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    ResponseEntity<List<User>> getAll(){
        List<User> users = service.getAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User user = service.insert(userDTO);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<User>update(@PathVariable Integer id, @RequestBody UserDTO newUser){
        return service.update(newUser,id)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    ResponseEntity<User>updateByField(@PathVariable Integer id, @RequestBody Map<String, Object>fields){
        return service.updateByField(id,fields)
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
