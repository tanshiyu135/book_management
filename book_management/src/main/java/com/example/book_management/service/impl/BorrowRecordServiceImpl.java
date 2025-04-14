package com.example.book_management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.book_management.dto.BorrowRecordDTO;
import com.example.book_management.entity.BorrowRecord;
import com.example.book_management.mapper.BorrowRecordMapper;
import com.example.book_management.service.BorrowRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowRecordServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowRecord>
        implements BorrowRecordService {
    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public List<BorrowRecordDTO> getAllWithUserAndBook() {
        return borrowRecordMapper.getAllWithUserAndBook();
    }


    @Override
    public boolean returnBook(Long id) {
        BorrowRecord record = borrowRecordMapper.selectById(id);
        if (record != null && "borrowed".equals(record.getStatus())) {
            record.setStatus("returned");
            record.setReturnDate(LocalDateTime.now());
            return borrowRecordMapper.updateById(record) > 0;
        }
        return false;
    }

    @Override
    public boolean renewBook(Long id) {
        BorrowRecord record = borrowRecordMapper.selectById(id);
        if (record != null && record.getRenewCount() < 1 && "borrowed".equals(record.getStatus())) {
            record.setDueDate(record.getDueDate().plusDays(14));
            record.setRenewCount(record.getRenewCount() + 1);
            record.setRenewedAt(LocalDateTime.now());
            return borrowRecordMapper.updateById(record) > 0;
        }
        return false;
    }
}