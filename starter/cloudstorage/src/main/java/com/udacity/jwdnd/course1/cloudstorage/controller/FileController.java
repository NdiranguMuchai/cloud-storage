package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserActionMessages;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/files")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private  final FileService fileService;
    private final UserService userService;
    private final UserActionMessages userActionMessages;

    public FileController(FileService fileService, UserService userService, UserActionMessages userActionMessages) {
        this.fileService = fileService;
        this.userService = userService;
        this.userActionMessages = userActionMessages;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                             File file,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) throws Exception{

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        file.setUserId(userId);

        if (fileService.findOne(multipartFile.getOriginalFilename()) !=null ){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.FILE_NAME_EXISTS);

        }else if (multipartFile.getOriginalFilename().isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.NO_FILE_SELECTED);
//            return "redirect:result";
        }
        else {

            try {
                fileService.upload(file, multipartFile);

                redirectAttributes.addFlashAttribute("successMessage", userActionMessages.FILE_UPLOAD_SUCCESS);
                return "redirect:/result";

            }catch (Exception e){
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.FILE_UPLOAD_ERROR);
                return "redirect:/result";
            }

        }

        return "redirect:/result";
    }

    @RequestMapping("/{fileId}/delete")
    public String deleteCredential(@PathVariable Integer fileId, RedirectAttributes redirectAttributes){

        try{
            fileService.delete(fileId);

            redirectAttributes.addFlashAttribute("successMessage", userActionMessages.FILE_DELETE_SUCCESS);
            return "redirect:/result";

        }catch (Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", userActionMessages.FILE_DELETE_ERROR);
            return "redirect:/result";
        }

    }

    @RequestMapping("/{fileId}/view")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId){
        try {
            File file = fileService.findById(fileId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(new ByteArrayResource(file.getFileData()));

        }catch (Exception e){
            logger.error("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
