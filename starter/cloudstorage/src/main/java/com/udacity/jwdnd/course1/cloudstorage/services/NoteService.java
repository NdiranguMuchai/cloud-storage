package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public List<Note> getNotes(Integer userId)
    {
        return noteMapper.getNotes(userId);
    }

    public void update(Note note){
        note.setNoteId(note.getNoteId());
        note.setUserId(note.getUserId());
        noteMapper.updateNote(note);
    }

    public Note findOne(Integer noteId){
        return noteMapper.findOne(noteId);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }

}
