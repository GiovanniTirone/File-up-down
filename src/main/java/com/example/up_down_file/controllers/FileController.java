package com.example.up_down_file.controllers;


import com.example.up_down_file.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download")
    public @ResponseBody byte[] download (@RequestParam String fileName, HttpServletResponse response) throws IOException {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension)
        {
            case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
            case "jpg","jpeg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
        return fileService.download(fileName);
    }

    @PostMapping("/upload")
    public String upload (@RequestParam MultipartFile file) throws Exception
    {
        return fileService.upload(file);
    }

}
