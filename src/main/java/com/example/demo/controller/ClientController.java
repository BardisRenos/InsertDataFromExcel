package com.example.demo.controller;

import com.example.demo.resources.Client;
import com.example.demo.service.ExcelService;
import com.example.demo.service.ExcelUtil;
import com.example.demo.service.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/excel")
@RequiredArgsConstructor
public class ClientController {

    private final ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<Response> insertFile(@RequestParam("file")MultipartFile file) throws IOException {

        if(ExcelUtil.isExcelFormat(file)) {
            excelService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Uploaded the file successfully: " + file.getOriginalFilename()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Please upload an excel file"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clientList = excelService.getAllClients();

            if (clientList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
