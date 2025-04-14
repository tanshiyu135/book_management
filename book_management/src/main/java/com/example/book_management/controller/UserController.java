package com.example.book_management.controller;

import com.example.book_management.entity.User;
import com.example.book_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        user.setId(null); // 🔐 强制清除前端传来的 ID，防止覆盖数据库自增
        System.out.println("接收到注册信息: " + user);
        return userService.save(user);
    }

    // 登录
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    // 获取所有用户
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.list();
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // 更新用户信息（昵称、手机号、密码）
    @PutMapping("/{id}/update")
    public boolean updateUser(@PathVariable Long id, @RequestBody User user) {
        // 这里可以根据id进行判断，确保修改的是正确的用户
        System.out.println("接收到更新请求：" + user); // 👈 添加这一行
        user.setId(id);  // 确保更新的是传入的用户
        return userService.updateById(user);
    }

    // 删除用户（管理员）
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.removeById(id);
    }
}
