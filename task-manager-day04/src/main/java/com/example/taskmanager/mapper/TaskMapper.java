package com.example.taskmanager.mapper;

import com.example.taskmanager.entity.Task;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TaskMapper {
    
    // 创建任务
    @Insert("INSERT INTO task (title, status, created_at) VALUES (#{title}, #{status}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);
    
    // 根据ID查询任务
    @Select("SELECT * FROM task WHERE id = #{id}")
    Task selectById(Long id);
    
    // 查询所有任务
    @Select("SELECT * FROM task ORDER BY created_at DESC")
    List<Task> selectAll();
    
    // 更新任务
    @Update("UPDATE task SET title = #{title}, status = #{status} WHERE id = #{id}")
    int update(Task task);
    
    // 删除任务
    @Delete("DELETE FROM task WHERE id = #{id}")
    int deleteById(Long id);
}