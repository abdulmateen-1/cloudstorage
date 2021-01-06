package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileMapper extends UserMapper{ ;

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    Files getFileByName(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    Files getFileById(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<Files> getAllFilesByUserId(Integer userId);

    @Insert("INSERT INTO FILES (filename, contentType, fileSize, userId, fileData) " +
            "VALUES(#{null}, #{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer fileSaver(Files files, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(int fileId);

}
