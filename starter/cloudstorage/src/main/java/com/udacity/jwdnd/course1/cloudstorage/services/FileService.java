package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<File> listAll(){
        return fileMapper.list();
    }

    public File findById(Integer id){
        return fileMapper.findById(id);
    }

    @Transactional
    public void upload(File file, MultipartFile multipartFile){
        try {
            Byte [] byteObjects = new Byte[multipartFile.getBytes().length];

            int value = 0;
            for (byte byt : multipartFile.getBytes()){
                byteObjects[value++] = byt;
            }

            file.setFileData(byteObjects);
            fileMapper.upload(file);

        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }

//
//    public File update(File file, MultipartFile multipartFile) throws Exception{
//        if (file.getFileId() == null){
//            throw  new Exception("Id does not exist");
//        }else {
//            saveFileData(file, multipartFile);
//        }
//        file.setFileId(file.getFileId());
//        file.setUserId(file.getUserId());
//        return fileMapper.update(file);
//    }
//@Transactional
//    public void saveFileData(File file, MultipartFile multipartFile) {
//        try {
//            Byte [] byteObjects = new Byte[multipartFile.getBytes().length];
//
//            int value = 0;
//            for (byte byt : multipartFile.getBytes()){
//                byteObjects[value++] = byt;
//            }
//            file.setFileData(byteObjects);
//
//        }catch (IOException e){
//            logger.error(e.getMessage());
//        }
//    }

    public void delete(Integer id){
        fileMapper.delete(id);
    }
}
