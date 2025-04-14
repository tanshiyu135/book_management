package com.example.book_management.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.book_management.entity.Book;
import com.example.book_management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    // 获取所有图书
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.list();
    }

    // 获取单本图书详情
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    // 新增图书（管理员）
    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    // 修改图书信息（管理员）
    @PutMapping
    public boolean updateBook(@RequestBody Book book) {
        return bookService.updateById(book);
    }

    // 删除图书（管理员）
    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable Long id) {
        return bookService.removeById(id);
    }

    // 根据类别或关键词搜索图书（关键词可为空）
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) String category) {
        QueryWrapper<Book> query = new QueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            query.lambda().like(Book::getTitle, keyword)
                    .or().like(Book::getAuthor, keyword);
        }
        if (category != null && !category.isEmpty()) {
            query.lambda().eq(Book::getCategory, category);
        }
        return bookService.list(query);
    }
}
