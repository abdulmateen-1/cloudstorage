package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NoteMapper {
    //Get Specific note by note Id
    @Select(("SELECT * FROM NOTES WHERE noteId = #{noteId}"))
    Note getByNoteId(int noteId);

    //Get All Notes From Notes db by userId
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> findNoteByUserId(int userId);

    //Insert New Note into NOTES db by specified noteId
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void addNote(Note note);

    //EDIT/UPDATE note title with changes made by specific note ID
    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription}" +
            "WHERE noteid=#{noteId}")
    void updateNote(Note note);

    //DELETE specific note by noteId
    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int deleteNoteByNoteId(@Param("noteId") int noteId);
}
