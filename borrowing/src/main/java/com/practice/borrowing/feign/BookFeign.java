package com.practice.borrowing.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "http://localhost:8081", path = "library")
public interface BookFeign {
    @GetMapping("/get_one/{id}")
    Book getOne(@PathVariable Integer id);

}
