package com.practice.user.service;

import com.practice.user.dto.UserDTO;
import com.practice.user.dto.UserMapper;
import com.practice.user.entity.User;
import com.practice.user.exceptions.UserNotFoundException;
import com.practice.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserMapper mapper;


    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<User> getOne(Integer id) {
        Optional<User> user = Optional.ofNullable(repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)));
        return user;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public String insert(UserDTO userDTO) {
        repository.save(mapper.toUser(userDTO));
        return "The user was created correctly";
    }

    public Optional<User> update(UserDTO userDTO, Integer id) {
        Optional<User> user = repository.findById(id);
        user.map(newUser -> {
            newUser.setName(userDTO.getName());
            newUser.setDocument(userDTO.getDocument());
            newUser.setTelephone(userDTO.getTelephone());
            return repository.save(newUser);

        }).orElseThrow(() -> new UserNotFoundException(id));
        return null;
    }

    public Optional<User> updateByField(Integer id, Map<String, Object> fields) {
        Optional<User> existingUser = repository.findById(id);

        if (existingUser.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser.get(), value);

            });
            repository.save(existingUser.get());
        }
        return null;
    }

    public String deleteUser(Integer id) {
        Optional<User> user = Optional.ofNullable(repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)));
        if(user.isPresent()){
            repository.deleteById(id);
            return "User was deleted successfully";
        }
        return "User doesn't found";
    }
}