package com.example.book_management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.book_management.entity.BorrowRecord;
import com.example.book_management.dto.BorrowRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
    @Select("SELECT br.id, br.user_id, u.nickname, br.book_id, b.title AS bookTitle, " +
            "br.borrow_date, br.due_date, br.return_date, br.renewed_at, " +
            "br.status, br.renew_count " +
            "FROM borrow_record br " +
            "JOIN user u ON br.user_id = u.id " +
            "JOIN book b ON br.book_id = b.id")
    List<BorrowRecordDTO> getAllWithUserAndBook();

}
