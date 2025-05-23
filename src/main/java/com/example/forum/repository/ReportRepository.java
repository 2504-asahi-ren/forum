package com.example.forum.repository;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ReportRepository extends JpaRepository<Report, Integer> {
    public List<Report> findByCreatedDateBetweenOrderByUpdatedDateDesc(Date start, Date end);
    @Modifying
    @Query(value = "UPDATE report SET updated_date = :updateDate WHERE id = :id", nativeQuery = true)
     void updateUpdatedDate(@Param("id") Report id, @Param("updateDate") LocalDateTime updateDate);
}
