package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {
    private  final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, File file, Model model, Authentication authentication) throws Exception{
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        file.setUserId(userId);

        fileService.upload(file, multipartFile);

        return "redirect:/home";
    }

    @RequestMapping("/{fileId}/delete")
    public String deleteCredential(@PathVariable Integer fileId){
        fileService.delete(fileId);

        return "redirect:/home";
    }
}
