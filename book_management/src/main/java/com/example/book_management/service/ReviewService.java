package com.example.book_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book_management.dto.ReviewDTO;
import com.example.book_management.entity.Review;

import java.util.List;

public interface ReviewService extends IService<Review> {
    List<Review> getReviewsByBookId(Long bookId);
    List<Review> getReviewsByUserId(Long userId);
    List<ReviewDTO> getAllReviews();
    boolean deleteReviewById(Long id);
}
