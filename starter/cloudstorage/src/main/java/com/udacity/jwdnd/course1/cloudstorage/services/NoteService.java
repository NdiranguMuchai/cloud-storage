package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Set<Note> findAll(){
        Set<Note> noteSet = new HashSet<>();
        noteMapper.list().iterator().forEachRemaining(noteSet::add);
        return noteSet;
    }

    public Optional<Note> findById(Integer id)throws Exception{
        noteMapper.findById(id).orElseThrow(()-> new Exception("Note with id "+id+" not found"));

        return noteMapper.findById(id);
    }

    public Integer save(Note note){

        return noteMapper.save(note);
    }

    public Integer update(Note note) throws Exception{
        noteMapper.findById(note.getNoteId()).orElseThrow(()-> new Exception("Note with id "+note.getNoteId()+" not found"));
        note.setNoteId(note.getNoteId());
        note.setUserId(note.getUserId());

        return noteMapper.update(note);
    }

    public void  delete(Integer id) throws Exception{
        noteMapper.findById(id).orElseThrow(()-> new Exception("Note with id "+id+" not found"));

        noteMapper.delete(id);
    }
}
