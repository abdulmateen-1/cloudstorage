package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Controller
public class NotesController {
    private final NotesService notesService;
    private final UserService userService;

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("creating a notes controller bean");
    }
     // Creates and edit new note
    @ModelAttribute
    public NoteDTO getNoteDTO() {
        return new NoteDTO();
    }

    @PostMapping("/home/note/newNote")
    public String postNewNote(@ModelAttribute("noteDTO") NoteDTO note, Authentication auth, Model model) {
        //Adding a new note
        String message;
        if (note.getNoteId() == null) {
            notesService.addNote(note, auth);
            model.addAttribute("updateSuccess", true);
        }
        else {
            notesService.updateNote(note);
            message = "note successfully edited";
            model.addAttribute("updateSuccess", message);
        }


        return "result";
    }

    @GetMapping("/home/note/delete/{noteId}")
    public String delete(@PathVariable("noteId") int noteId, Model model) {

        String message = null;

        int currentNoteId = notesService.deleteNote(noteId);

        if (currentNoteId < 1)
            message = "There was an error deleting the note.";

        if (message == null)
            model.addAttribute("updateSuccess", true);
        else
            model.addAttribute("updateFail", message);

        return "result";
    }

}
