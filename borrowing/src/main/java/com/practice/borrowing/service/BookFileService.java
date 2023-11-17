package com.practice.borrowing.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.practice.borrowing.entity.BookFile;
import com.practice.borrowing.messages.FileMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BookFileService {

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private GridFsTemplate template;


    public String addFile(MultipartFile upload) throws IOException {
        if (!upload.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException(FileMessage.INCORRECT_FILE.getMessage());
        }
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize",upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(),
                upload.getContentType(), metadata);

        return fileID.toString();
    }

    public BookFile downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        BookFile loadFile = new BookFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;
    }

}
