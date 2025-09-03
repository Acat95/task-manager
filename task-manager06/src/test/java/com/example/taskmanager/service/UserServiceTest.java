package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserService的单元测试类
 * 使用Mockito模拟依赖的UserMapper
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
    }
    
    @Test
    void testRegister_Success() {
        // 准备测试数据
        when(userMapper.selectByUsername("testuser")).thenReturn(null); // 用户名不存在
        when(userMapper.insert(any(User.class))).thenReturn(1);
        
        // 执行测试
        int result = userService.register(testUser);
        
        // 验证结果
        assertEquals(1, result);
        verify(userMapper).selectByUsername("testuser");
        verify(userMapper).insert(testUser);
    }
    
    @Test
    void testRegister_UsernameExists() {
        // 准备测试数据 - 模拟用户名已存在
        when(userMapper.selectByUsername("testuser")).thenReturn(testUser);
        
        // 执行测试
        int result = userService.register(testUser);
        
        // 验证结果
        assertEquals(-1, result); // -1表示用户名已存在
        verify(userMapper).selectByUsername("testuser");
        verify(userMapper, never()).insert(any(User.class));
    }
    
    @Test
    void testRegister_WithEmptyUsername_ShouldThrowException() {
        // 准备测试数据 - 空用户名
        User invalidUser = new User();
        invalidUser.setUsername(""); // 空用户名
        invalidUser.setPassword("password123");
        
        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(invalidUser);
        });
        
        // 验证mapper没有被调用
        verify(userMapper, never()).selectByUsername(anyString());
        verify(userMapper, never()).insert(any(User.class));
    }
    
    @Test
    void testRegister_WithEmptyPassword_ShouldThrowException() {
        // 准备测试数据 - 空密码
        User invalidUser = new User();
        invalidUser.setUsername("newuser");
        invalidUser.setPassword(""); // 空密码
        
        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> {
            userService.register(invalidUser);
        });
        
        // 验证mapper没有被调用
        verify(userMapper, never()).selectByUsername(anyString());
        verify(userMapper, never()).insert(any(User.class));
    }
    
    @Test
    void testFindByUsername_Success() {
        // 准备测试数据
        when(userMapper.selectByUsername("testuser")).thenReturn(testUser);
        
        // 执行测试
        User result = userService.findByUsername("testuser");
        
        // 验证结果
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("password123", result.getPassword());
        verify(userMapper).selectByUsername("testuser");
    }
    
    @Test
    void testFindByUsername_NotFound() {
        // 准备测试数据 - 模拟用户不存在
        when(userMapper.selectByUsername("nonexistent")).thenReturn(null);
        
        // 执行测试
        User result = userService.findByUsername("nonexistent");
        
        // 验证结果
        assertNull(result);
        verify(userMapper).selectByUsername("nonexistent");
    }
    
    @Test
    void testFindById_Success() {
        // 准备测试数据
        when(userMapper.selectById(1L)).thenReturn(testUser);
        
        // 执行测试
        User result = userService.findById(1L);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userMapper).selectById(1L);
    }
    
    @Test
    void testFindById_WithInvalidId() {
        // 执行测试 - 使用无效ID
        User result = userService.findById(-1L);
        
        // 验证结果
        assertNull(result);
        verify(userMapper, never()).selectById(anyLong());
    }
}