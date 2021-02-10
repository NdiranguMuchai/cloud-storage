package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    List<File> list();

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findById(Integer id);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void upload(File file);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contenttype}," +
            " filesize = #{filesize}, userid = #{userid}, filedata = #{filedata} WHERE fileId = #{fileId}")
    File update(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(Integer fileId);
}
