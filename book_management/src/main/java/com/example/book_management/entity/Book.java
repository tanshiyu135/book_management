package com.example.book_management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("book")
public class Book {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String category;
    private String summary;
    private String status; // 'available' or 'unavailable'
    private Integer quantity;
    private Integer availableQty;
    private Integer borrowCount;
    private String coverUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
