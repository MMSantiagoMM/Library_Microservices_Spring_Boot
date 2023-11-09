package com.practice.borrowing.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-service", url = "http://localhost:8081", path = "book")
public interface BookFeign {
    @GetMapping("/{id}")
    Book getOne(@PathVariable Integer id);

    @GetMapping("/get_several/{values}")
    List<Book> getSeveral(@PathVariable Integer[] values);



}
