package com.example.book_management.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Long userId;
    private String nickname;   // 用户昵称
    private Long bookId;
    private String bookTitle;  // 图书标题
    private String comment;
    private Integer rating;
    private LocalDateTime createdAt;
}
