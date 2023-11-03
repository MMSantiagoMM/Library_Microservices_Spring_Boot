package com.practice.borrowing.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",url = "http://localhost:8082",path = "user")
public interface UserFeign {
    @GetMapping("/get_one/{id}")
    User getOne(@PathVariable Integer id);
}
