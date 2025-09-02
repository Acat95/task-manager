package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    
    private final TaskMapper taskMapper;
    
    @Autowired
    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }
    
    // 创建任务
    public int createTask(Task task) {
        return taskMapper.insert(task);
    }
    
    // 根据ID查询任务
    public Task getTaskById(Long id) {
        return taskMapper.selectById(id);
    }
    
    // 查询所有任务
    public List<Task> getAllTasks() {
        return taskMapper.selectAll();
    }
    
    // 更新任务
    public int updateTask(Task task) {
        return taskMapper.update(task);
    }
    
    // 删除任务
    public int deleteTask(Long id) {
        return taskMapper.deleteById(id);
    }
}