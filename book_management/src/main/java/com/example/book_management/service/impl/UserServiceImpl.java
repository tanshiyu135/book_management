package com.example.book_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.book_management.entity.User;
import com.example.book_management.mapper.UserMapper;
import com.example.book_management.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("username", username).eq("password", password);
        return this.getOne(query);
    }
}
