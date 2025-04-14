package com.example.book_management.mapper;

import com.example.book_management.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BookMapper extends BaseMapper<Book> {

    @Select("SELECT category, COUNT(*) as count FROM book GROUP BY category")
    List<Map<String, Object>> selectCategoryStats();

    @Select("SELECT id, title, borrow_count as borrowCount " +
            "FROM book " +
            "ORDER BY borrow_count DESC " +
            "LIMIT 10")
    List<Map<String, Object>> selectHotBooks();
}