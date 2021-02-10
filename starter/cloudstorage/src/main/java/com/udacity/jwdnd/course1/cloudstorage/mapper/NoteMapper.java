package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
import java.util.Set;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    Set<Note> list();

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Optional<Note> findById(Integer id);

    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}), #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer save(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
    Integer update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer id);
}
