package com.example.book_management.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.sql.Timestamp;

@Data
@TableName("user")
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.AUTO)// ✅ 自动使用数据库的自增 ID
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String role; // admin / reader
    private Timestamp createdAt;
}
