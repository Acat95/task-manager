package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.mapper.TaskMapper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TaskService的单元测试类
 * 使用Mockito模拟依赖的TaskMapper
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;
    
    @InjectMocks
    private TaskService taskService;
    
    private Task testTask;
    
    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("测试任务");
        testTask.setStatus("TODO");
    }
    
    @Test
    void testCreateTask_Success() {
        // 准备测试数据
        when(taskMapper.insert(any(Task.class))).thenReturn(1);
        
        // 执行测试
        int result = taskService.createTask(testTask);
        
        // 验证结果
        assertEquals(1, result);
        verify(taskMapper).insert(testTask);
    }
    
    @Test
    void testCreateTask_WithEmptyTitle_ShouldThrowException() {
        // 准备测试数据 - 空标题任务
        Task invalidTask = new Task();
        invalidTask.setTitle(""); // 空标题
        
        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(invalidTask);
        });
        
        // 验证mapper没有被调用
        verify(taskMapper, never()).insert(any(Task.class));
    }
    
    @Test
    void testGetTaskById_Success() {
        // 准备测试数据
        when(taskMapper.selectById(1L)).thenReturn(testTask);
        
        // 执行测试
        Task result = taskService.getTaskById(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("测试任务", result.getTitle());
        assertEquals("TODO", result.getStatus());
        verify(taskMapper).selectById(1L);
    }
    
    @Test
    void testGetTaskById_NotFound() {
        // 准备测试数据 - 模拟任务不存在
        when(taskMapper.selectById(999L)).thenReturn(null);
        
        // 执行测试
        Task result = taskService.getTaskById(999L);
        
        // 验证结果
        assertNull(result);
        verify(taskMapper).selectById(999L);
    }
    
    @Test
    void testGetTaskById_WithInvalidId() {
        // 执行测试 - 使用无效ID
        Task result = taskService.getTaskById(-1L);
        
        // 验证结果
        assertNull(result);
        verify(taskMapper, never()).selectById(anyLong());
    }
    
    @Test
    void testGetAllTasks() {
        // 准备测试数据
        List<Task> taskList = Arrays.asList(
            testTask,
            new Task("测试任务2", "IN_PROGRESS")
        );
        when(taskMapper.selectAll()).thenReturn(taskList);
        
        // 执行测试
        List<Task> result = taskService.getAllTasks();
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskMapper).selectAll();
    }
    
    @Test
    void testFindAllTasks_Pagination() {
        // 准备测试数据
        List<Task> taskList = Arrays.asList(
            testTask,
            new Task("测试任务2", "IN_PROGRESS"),
            new Task("测试任务3", "DONE")
        );
        when(taskMapper.selectAll()).thenReturn(taskList);
        
        // 执行测试 - 第1页，每页2条
        PageInfo<Task> result = taskService.findAllTasks(1, 2);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.getSize()); // 当前页大小
        assertEquals(3, result.getTotal()); // 总记录数
        assertEquals(2, result.getPages()); // 总页数
        verify(taskMapper).selectAll();
    }
    
    @Test
    void testUpdateTask_Success() {
        // 准备测试数据
        when(taskMapper.selectById(1L)).thenReturn(testTask);
        when(taskMapper.update(any(Task.class))).thenReturn(1);
        
        // 修改任务状态
        testTask.setStatus("DONE");
        
        // 执行测试
        int result = taskService.updateTask(testTask);
        
        // 验证结果
        assertEquals(1, result);
        verify(taskMapper).selectById(1L);
        verify(taskMapper).update(testTask);
    }
    
    @Test
    void testUpdateTask_NotFound() {
        // 准备测试数据 - 模拟任务不存在
        when(taskMapper.selectById(999L)).thenReturn(null);
        
        // 执行测试
        Task nonExistentTask = new Task();
        nonExistentTask.setId(999L);
        nonExistentTask.setTitle("不存在的任务");
        nonExistentTask.setStatus("TODO");
        
        // 验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTask(nonExistentTask);
        });
        
        verify(taskMapper).selectById(999L);
        verify(taskMapper, never()).update(any(Task.class));
    }
    
    @Test
    void testDeleteTask_Success() {
        // 准备测试数据
        when(taskMapper.selectById(1L)).thenReturn(testTask);
        when(taskMapper.deleteById(1L)).thenReturn(1);
        
        // 执行测试
        int result = taskService.deleteTask(1L);
        
        // 验证结果
        assertEquals(1, result);
        verify(taskMapper).selectById(1L);
        verify(taskMapper).deleteById(1L);
    }
    
    @Test
    void testDeleteTask_NotFound() {
        // 准备测试数据 - 模拟任务不存在
        when(taskMapper.selectById(999L)).thenReturn(null);
        
        // 执行测试
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.deleteTask(999L);
        });
        
        verify(taskMapper).selectById(999L);
        verify(taskMapper, never()).deleteById(anyLong());
    }
}