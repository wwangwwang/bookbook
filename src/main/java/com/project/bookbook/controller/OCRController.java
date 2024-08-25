package com.project.bookbook.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.service.VisionService;

import groovy.util.logging.Slf4j;

@RestController
@Slf4j
public class OCRController {

    @Autowired
    private VisionService visionService;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(OCRController.class);

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("imageFile") MultipartFile file) {
        try {
            String text = visionService.extractTextFromImage(file.getInputStream());
            Map<String, String> response = new HashMap<>();
            response.put("text", text);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("Error reading file: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Failed to read file: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("Error processing image: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "Failed to extract text: " + e.getMessage()));
        }
    }
}

