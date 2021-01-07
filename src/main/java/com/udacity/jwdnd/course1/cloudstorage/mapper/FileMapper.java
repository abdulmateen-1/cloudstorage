package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileMapper{

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getByUserName(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getById(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getAllByUserId(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer store(File files, Integer userId);

    @Delete("DELETE FROM FILE WHERE fileId = #{fileId}")
    int delete(int fileId);

}
