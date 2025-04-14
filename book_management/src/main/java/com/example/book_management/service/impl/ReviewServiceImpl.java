package com.example.book_management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.book_management.dto.BorrowRecordDTO;
import com.example.book_management.dto.ReviewDTO;
import com.example.book_management.entity.Review;
import com.example.book_management.mapper.ReviewMapper;
import com.example.book_management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Override
    public List<Review> getReviewsByBookId(Long bookId) {
        return this.list(new QueryWrapper<Review>().eq("book_id", bookId));
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {

        return this.list(new QueryWrapper<Review>().eq("user_id", userId));
    }

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewMapper.getAllWithUserAndBook();
    }

    @Override
    public boolean deleteReviewById(Long id) {
        return reviewMapper.deleteById(id) > 0;
    }
}
