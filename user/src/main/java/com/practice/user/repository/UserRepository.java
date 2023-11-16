package com.practice.user.repository;

import com.practice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    @Query(value = "SELECT * FROM users u WHERE upper(u.name) LIKE upper" +
            "(concat('%', ?1, '%'))", nativeQuery = true)
    List<User> getByName(String name);

}
