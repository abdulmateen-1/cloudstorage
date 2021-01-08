package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NoteMapper {

    //Get All Notes From Notes db by userId
    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> findNoteByUserId(int userId);

    //Insert New Note into NOTES db by specified noteId
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNoteByNoteId(Note note);

    //EDIT/UPDATE note title with changes made by specific note ID
    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription}" +
            "WHERE noteid=#{noteId}")
    int updateNoteByNoteId(Note note);

    //DELETE specific note by noteId
    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int deleteNoteByNoteId(@Param("noteId") int noteId);
}
