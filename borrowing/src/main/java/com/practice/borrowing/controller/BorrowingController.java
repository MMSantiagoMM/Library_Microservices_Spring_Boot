package com.practice.borrowing.controller;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.BookFile;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.service.BookFileService;
import com.practice.borrowing.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("library")
public class BorrowingController {

    @Autowired
    private BorrowingService service;

    @Autowired
    private BookFileService bookFileService;


    @GetMapping("/get_alls")
    List<Borrowing>getAll(){
        return service.getAllBorrowings();
    }

    @GetMapping("/get_one/{id}")
    Optional<Borrowing> getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping("/insert")
    String create(@RequestBody BorrowingDTO borrowingDTO){
        return service.insert(borrowingDTO);
    }

    @PutMapping("/update/{id}")
    String update (@RequestBody BorrowingDTO newBorrowing, @PathVariable Integer id){
        service.updateBorrowing(newBorrowing,id);
        return "The borrowing was update successfully";
    }

    @PatchMapping("/update_field/{id}")
    String updateByField(@PathVariable Integer id, @RequestBody Map<String,Object>fields){
        service.updateByField(id,fields);
        return "The borrowing's field was updated";
    }

    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable Integer id){
        return service.deleteBorrowing(id);
    }

    @PostMapping("/insert_book")
    ResponseEntity<?> uploadBook(@RequestParam("file")MultipartFile file)throws IOException{
        return new ResponseEntity<>(bookFileService.addFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        BookFile loadFile = bookFileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }


}
