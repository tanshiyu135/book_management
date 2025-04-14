package com.example.book_management.controller;

import com.example.book_management.dto.BorrowRecordDTO;
import com.example.book_management.dto.ReviewDTO;
import com.example.book_management.entity.Review;
import com.example.book_management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 添加评价
    @PostMapping
    public boolean addReview(@RequestBody Review review) {
        return reviewService.save(review);
    }
    // 获取所有评论（管理员查看）
    @GetMapping("/all")
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }



    // 获取某本书的评价
    @GetMapping("/book/{bookId}")
    public List<Review> getByBookId(@PathVariable Long bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    // 获取某个用户的评价
    @GetMapping("/user/{userId}")
    public List<Review> getByUserId(@PathVariable Long userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    // 管理员删除评论
    @DeleteMapping("/{id}")
    public boolean deleteReview(@PathVariable Long id) {
        return reviewService.removeById(id);
    }

}
