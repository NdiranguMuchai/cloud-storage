package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home/file")
public class FileController {
    private  final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, File file, Model model){

        fileService.upload(file, multipartFile);
        if (file.getFiledata()!= null){
            model.addAttribute("successMessage", true);
        }else
            model.addAttribute("errorMessage");
        return "result";
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("fileList", fileService.listAll());
        return "home";
    }
}
