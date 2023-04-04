package com.desafio.ai.controller;

import com.desafio.ai.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
public class FileController {
    final FileService fileService;

    @PostMapping(value = "/save")
    public Object salvarFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.createFile(file);
    }

    @GetMapping(value = "/findall")
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(fileService.findAllFiles());
    }

    @GetMapping(value = "/findbyid/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        return ResponseEntity.ok(fileService.findFile(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(fileService.deleteFile(id));
    }

    @GetMapping(value = "/content/{id}")
    public ResponseEntity<Object> contentById(@PathVariable String id) {
        return ResponseEntity.ok(fileService.findContentFile(id));
    }
}
