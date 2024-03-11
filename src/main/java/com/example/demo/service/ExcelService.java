package com.example.demo.service;

import com.example.demo.dao.ClientRepository;
import com.example.demo.resources.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void insertData(MultipartFile file) throws IOException {
        List<Client> clients = ExcelUtil.excelToData(file.getInputStream());
        clientRepository.saveAll(clients);
    }

    public List<Client> getByFirstName(String name) {

        return clientRepository.findByName(name);
    }
}
