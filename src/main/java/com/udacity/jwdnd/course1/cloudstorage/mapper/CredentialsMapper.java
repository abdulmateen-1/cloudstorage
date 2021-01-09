package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CredentialsMapper {
    // Get all credentials saved to the database
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getAllCredentials(int userId);

    //Select a specific credential by fileId
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialByFileId(int fileId);

    //Insert new Credential into the database
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void addCredential(Credential credential);

    //Edit/Update the credential made by a specific file id
    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} " +
            "WHERE credentialid=#{credentialId}")
    void updateCredentials(Credential credential);

    //Delete credential
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    int deleteCredential(@Param("credentialId") int credentialId);
}
