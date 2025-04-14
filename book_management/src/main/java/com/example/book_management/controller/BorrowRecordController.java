package com.example.book_management.controller;

import com.example.book_management.dto.BorrowRecordDTO;
import com.example.book_management.service.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin
public class BorrowRecordController {

    @Resource
    private BorrowRecordService borrowRecordService;

    @GetMapping
    public List<BorrowRecordDTO> getAllBorrowRecords() {
        return borrowRecordService.getAllWithUserAndBook();
    }


    @PutMapping("/return/{id}")
    public boolean returnBook(@PathVariable Long id) {
        return borrowRecordService.returnBook(id);
    }

    @PutMapping("/renew/{id}")
    public boolean renewBook(@PathVariable Long id) {
        return borrowRecordService.renewBook(id);
    }
}