package com.example.up_down_file.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {


    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;


    public String upload(MultipartFile file) throws Exception {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID() + "." + extension;
        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new Exception("The final folder doesn't exists");
        if(!finalFolder.isDirectory()) throw new Exception("The final folder isn't a directory");
        File finalDestination = new File(fileRepositoryFolder + "\\" + newFileName);
        if(finalDestination.exists()) throw new Exception("Conflict");
        file.transferTo(finalDestination);
        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepo = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepo.exists()) throw new IOException("File doesn't exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepo));
    }

}
