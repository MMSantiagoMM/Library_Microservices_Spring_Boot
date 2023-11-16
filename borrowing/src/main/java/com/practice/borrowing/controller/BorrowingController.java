package com.practice.borrowing.controller;


import com.practice.borrowing.dto.BorrowingDTO;
import com.practice.borrowing.entity.BookFile;
import com.practice.borrowing.entity.Borrowing;
import com.practice.borrowing.service.BookFileService;
import com.practice.borrowing.service.BorrowingService;
import org.apache.coyote.Response;
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
@RequestMapping("borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService service;

    @Autowired
    private BookFileService bookFileService;


    @GetMapping
    ResponseEntity<List<Borrowing>>getAll(@RequestParam(required = false) String nameUser,
                                          @RequestParam(required = false) String title){
        List<Borrowing> borrowings = service.getAllBorrowings(nameUser,title);
        return new ResponseEntity<>(borrowings,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Borrowing> getOne(@PathVariable Integer id){
        return service.getOne(id)
                .map(borrowing -> new ResponseEntity<>(borrowing,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<Borrowing>create(@RequestBody BorrowingDTO borrowingDTO){
        Borrowing borrowing = service.insert(borrowingDTO);
        return new ResponseEntity<>(borrowing, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Borrowing> update (@RequestBody BorrowingDTO newBorrowing, @PathVariable Integer id){
        return service.updateBorrowing(newBorrowing,id)
                .map(borrowing -> new ResponseEntity<>(borrowing,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PatchMapping("/{id}")
    ResponseEntity<Borrowing> updateByField(@PathVariable Integer id,
                                            @RequestBody BorrowingDTO updatedBorrowing){
        return service.updateByField(id,updatedBorrowing)
                .map(borrowing -> new ResponseEntity<>(borrowing,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        if(service.deleteBorrowing(id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/file")
    ResponseEntity<?> uploadBook(@RequestParam("file")MultipartFile file)throws IOException{
        return new ResponseEntity<>(bookFileService.addFile(file), HttpStatus.OK);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        BookFile loadFile = bookFileService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }


}
