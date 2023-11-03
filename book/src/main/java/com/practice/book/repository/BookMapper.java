package com.practice.book.repository;


import com.practice.book.dto.BookDTO;
import com.practice.book.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id",ignore = true)
    Book toBook(BookDTO bookDTO);
}
