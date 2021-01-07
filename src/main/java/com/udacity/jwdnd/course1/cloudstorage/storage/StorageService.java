package com.udacity.jwdnd.course1.cloudstorage.storage;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface StorageService {

    Integer store(MultipartFile file, Integer userId);

    File getByUserName(String username);

    List<File> getAllByUserId(Integer userId);

    File getById(int fileId);

    void delete(int fileId);


}
