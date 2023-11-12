package com.practice.borrowing.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookFeign {
    @GetMapping("book/get_one/{id}")
    Book getOne(@PathVariable Integer id);

    @GetMapping("book/get_several/{values}")
    List<Book> getSeveral(@PathVariable Integer[] values);



}
