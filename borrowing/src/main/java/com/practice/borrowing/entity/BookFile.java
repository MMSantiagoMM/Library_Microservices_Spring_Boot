package com.practice.borrowing.entity;


import lombok.Data;

@Data
public class BookFile {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;


}
