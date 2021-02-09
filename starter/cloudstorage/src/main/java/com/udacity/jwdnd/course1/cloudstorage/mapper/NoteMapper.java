package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.Set;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    Set<Note> list();

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Optional<Note> findById(Integer id);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}), #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer save(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
    Integer update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void delete(Integer id);
}
