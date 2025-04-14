package com.example.book_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book_management.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
}
