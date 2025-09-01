package com.example.taskmanager.service;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    
    private final UserMapper userMapper;
    
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    // 用户注册
    public int register(User user) {
        // 简单检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return -1; // 用户名已存在
        }
        return userMapper.insert(user);
    }
    
    // 根据用户名查询用户
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    // 根据ID查询用户
    public User findById(Long id) {
        return userMapper.selectById(id);
    }
}