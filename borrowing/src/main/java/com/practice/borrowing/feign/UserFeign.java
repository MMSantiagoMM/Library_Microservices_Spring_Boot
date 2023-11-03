package com.practice.borrowing.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user")
public interface UserFeign {
    @GetMapping("/get_one/{id}")
    Optional<Book> getOne(@PathVariable Integer id);
}
