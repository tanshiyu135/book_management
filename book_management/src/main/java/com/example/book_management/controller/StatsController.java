package com.example.book_management.controller;

import com.example.book_management.entity.Book;
import com.example.book_management.service.BookService;
import com.example.book_management.service.BorrowRecordService;
import com.example.book_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowRecordService borrowRecordService;

    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userService.count());
        data.put("bookCount", bookService.count());
        data.put("availableBookCount", bookService.lambdaQuery().gt(Book::getAvailableQty, 0).count());
        data.put("borrowRecordCount", borrowRecordService.count());
        return data;
    }

}
