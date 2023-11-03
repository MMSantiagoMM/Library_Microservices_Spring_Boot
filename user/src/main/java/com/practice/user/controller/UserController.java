package com.practice.user.controller;


import com.practice.user.dto.UserDTO;
import com.practice.user.entity.User;
import com.practice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/get_one/{id}")
    Optional<User> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @GetMapping("/get_all")
    List<User> getAll(){
        return service.getAll();
    }

    @PostMapping("/insert")
    String createUser(@RequestBody UserDTO userDTO){
        return service.insert(userDTO);
    }

    @PutMapping("/update/{id}")
    Optional<User>update(@PathVariable Integer id, @RequestBody UserDTO newUser){
        return service.update(newUser,id);
    }

    @PatchMapping("/update_field/{id}")
    Optional<User>updateByField(@PathVariable Integer id, @RequestBody Map<String, Object>fields){
        return service.updateByField(id,fields);
    }

    @DeleteMapping("/delete/{id}")
    String deleteUser(@PathVariable Integer id){
        return service.deleteUser(id);
    }
}
