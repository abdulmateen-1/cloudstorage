package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileStorageService implements StorageService {

    private final FileMapper fileMapper;

    public FileStorageService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public Integer store(MultipartFile multipartFile, Integer userId) throws IOException {
        // Convert the uploaded file under MultipartFile into FileForm
        // before storing to db:
        // filename, contenttype, filesize, userid, filedata

        File file = new File(null, multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), String.valueOf(multipartFile.getSize()),
                userId, multipartFile.getBytes());

        return this.fileMapper.store(file);
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
    public int delete(int fileId) {
        return fileMapper.delete(fileId);
    }

    public boolean isFileNameAvailable(String filename) {
        return fileMapper.getByUserName(filename) == null;
    }
}
