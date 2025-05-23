package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReportRepository reportRepository;
    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentRepository.findAllByOrderByIdDesc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setContent(result.getContent());
            comment.setReport_id(result.getReport_id());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * レコード追加
     */
    @Transactional
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
        Report reportId = setReportEntity(reqComment);
        reportRepository.updateUpdatedDate(reportId, LocalDateTime.now());
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setContent(reqComment.getContent());
        comment.setReport_id(reqComment.getReport_id());
        return comment;
    }
    private Report setReportEntity(CommentForm reqComment) {
        Report report = new Report();
        report.setId(reqComment.getReport_id());
        return report;
    }

    /*
     *レコード削除
     */
    public void deleteComment(Integer id) {
        Comment deleteComment = deleteCommentEntity(id);
        commentRepository.delete(deleteComment);
    }
    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment deleteCommentEntity(Integer id) {
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
    /*
     * レコード1件取得
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }


}