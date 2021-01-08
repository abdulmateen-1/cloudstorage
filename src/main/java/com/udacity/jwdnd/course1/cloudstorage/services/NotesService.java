package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    @Autowired
    private UserService service;

    private final NoteMapper noteMapper;

    public NotesService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNote(Integer userId) throws Exception {
        List<Note> notes = noteMapper.findNoteByUserId(userId);
        if (notes == null)
            throw new Exception();
        return notes;
    }

    public Note getNote(int noteId) {
        return noteMapper.getByNoteId(noteId);
    }

    public void addNote(NoteDTO note, Authentication authentication) {

        //Add in a new Note
        User user = service.getUser(authentication.getName());
        int userId = user.getUserId();
        noteMapper.addNote(new Note(null, note.getNoteTitle(),
                note.getNoteDescription(), userId));

    }

    public void updateNote(NoteDTO noteDTO) {
        Note note = noteMapper.getByNoteId(noteDTO.getNoteId());
        note.setNoteTitle(noteDTO.getNoteTitle());
        note.setNonDescription(noteDTO.getNoteDescription());
        noteMapper.updateNote(note);
    }

    public int deleteNote(int noteId) {
        return noteMapper.deleteNoteByNoteId(noteId);
    }
}
