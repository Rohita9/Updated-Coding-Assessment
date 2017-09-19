package com.fileupload.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestUploadController {
	
	private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

    private static String FOLDER_TO_UPLOAD = "C://uploadedfiles//";

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("Please select a file to upload.", HttpStatus.OK);
        }

        try {
        	byte[] bytes = uploadfile.getBytes();
            Path path = Paths.get(FOLDER_TO_UPLOAD + uploadfile.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
        	logger.error("Exception while uploading file : "+e.getStackTrace(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.debug("File uploaded successfully. File Name: "+uploadfile.getOriginalFilename());
        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }
}
