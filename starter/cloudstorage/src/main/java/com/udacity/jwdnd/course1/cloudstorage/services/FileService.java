package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> listAll(Integer userId){

        return fileMapper.list(userId);
    }

    public File findById(Integer fileId){
        return fileMapper.findById(fileId);
    }

    public File findOne(String fileName){
        return fileMapper.findOne(fileName);
    }

    public int upload(File file, MultipartFile multipartFile) throws Exception{
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(String.valueOf(multipartFile.getSize()));
        file.setFileData(multipartFile.getBytes());

        return fileMapper.upload(file);
    }

    public void delete(Integer fileId){
        fileMapper.delete(fileId);
    }
}
