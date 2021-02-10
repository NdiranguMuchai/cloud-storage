package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping
    public String listAll(Model model){
        model.addAttribute("noteList", noteService.findAll());
        return "home";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model)throws Exception{
        noteService.findById(id).orElseThrow(()-> new Exception("Note with id "+id+ " not found"));

        model.addAttribute("noteElement", noteService.findById(id));
        return "home";
    }

  @RequestMapping("/new")
  public String newFile(Model model){
        model.addAttribute("note", new Note());
        return  "home";
  }

    @PostMapping
    public String save(@ModelAttribute Note note, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        note.setUserid(user.getUserId());
       noteService.save(note);
       return "result";
    }
}
