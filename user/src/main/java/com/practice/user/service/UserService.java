package com.practice.user.service;

import com.practice.user.dto.UserDTO;
import com.practice.user.dto.UserMapper;
import com.practice.user.entity.User;
import com.practice.user.exceptions.UserNotFoundException;
import com.practice.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

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

    public User insert(UserDTO userDTO) {
        return repository.save(mapper.toUser(userDTO));
    }

    public Optional<User> update(UserDTO userDTO, Integer id) {
        Optional<User> user = repository.findById(id);
        user.map(newUser -> {
            newUser.setName(userDTO.getName());
            newUser.setDocument(userDTO.getDocument());
            newUser.setPhoneNumber(userDTO.getPhoneNumber());
            return repository.save(newUser);

        }).orElseThrow(() -> new UserNotFoundException(id));
        return null;
    }

    public Optional<User> updateByField(Integer id, UserDTO updatedUserDTO) {
        Optional<User> existingUser = repository.findById(id);
        if (existingUser.isPresent()) {
            User existingUserEntity = existingUser.get();
            BeanUtils.copyProperties(updatedUserDTO, existingUserEntity,
                    getNullPropertyNames(updatedUserDTO));
            return Optional.of(repository.save(existingUserEntity));
        } else {
            return Optional.empty();
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public Boolean deleteUser(Integer id) {
        Optional<User> user = Optional.ofNullable(repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id)));
        if(user.isPresent()){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}