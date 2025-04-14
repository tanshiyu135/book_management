package com.example.book_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_management.entity.Review;
import com.example.book_management.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper extends BaseMapper<Review> {

    @Select("SELECT r.id, r.user_id, u.nickname, r.book_id, b.title AS bookTitle, " +
            "r.comment,  r.created_at " +
            "FROM review r " +
            "JOIN user u ON r.user_id = u.id " +
            "JOIN book b ON r.book_id = b.id")
    List<ReviewDTO> getAllWithUserAndBook();
}
