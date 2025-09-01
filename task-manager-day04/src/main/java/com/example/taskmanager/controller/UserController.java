package com.example.taskmanager.controller;

import com.example.taskmanager.entity.User;
import com.example.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        if (user.getUsername() == null || user.getPassword() == null) {
            response.put("success", false);
            response.put("message", "用户名和密码不能为空");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        int result = userService.register(user);
        if (result > 0) {
            response.put("success", true);
            response.put("message", "注册成功");
            response.put("userId", user.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == -1) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            response.put("success", false);
            response.put("message", "注册失败");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    // 用户登录 (简单实现，未加密)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null) {
            response.put("success", false);
            response.put("message", "用户不存在");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        // 简单密码比较，Day 4会使用加密和JWT
        if (existingUser.getPassword().equals(user.getPassword())) {
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("userId", existingUser.getId());
            response.put("username", existingUser.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "密码错误");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    // 根据ID查询用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            // 出于安全考虑，不返回密码
            user.setPassword(null);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}