package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileStorageService implements StorageService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public Integer store(MultipartFile multipartFile, Integer userId) {
        try {
            if (multipartFile.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
            return fileMapper.store(file, userId);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public File getByUserName(String username) {
        return fileMapper.getByUserName(username);
    }

    @Override
    public List<File> getAllByUserId(Integer userId) {
        return fileMapper.getAllByUserId(userId);
    }

    @Override
    public File getById(int fileId) {
        return fileMapper.getById(fileId);
    }

    @Override
    public void delete(int fileId) {
        fileMapper.delete(fileId);
    }
}
