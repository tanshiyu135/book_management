package com.example.book_management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book_management.dto.BorrowRecordDTO;
import com.example.book_management.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordService extends IService<BorrowRecord> {
    List<BorrowRecordDTO> getAllWithUserAndBook();

    boolean returnBook(Long id);
    boolean renewBook(Long id);

}