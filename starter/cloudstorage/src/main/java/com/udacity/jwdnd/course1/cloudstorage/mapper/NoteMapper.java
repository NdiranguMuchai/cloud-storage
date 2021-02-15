package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{title},#{description},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET title=#{noteTitle}, noteDescription =#{description} WHERE noteId =#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId =#{noteId}")
    void deleteNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note findOne(Integer noteId);


}
