package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * 指定した日付のレコード全件取得処理
     */
    public List<ReportForm> findAllReport(String startDate, String endDate) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start;
        Date end;
        try {
             start = sdFormat.parse(startDate + " 00:00:00");
             end = sdFormat.parse(endDate + " 23:59:59");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<Report> results = reportRepository.findByCreatedDateBetweenOrderByUpdatedDateDesc(start, end);
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreated_date(result.getCreatedDate());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        Date date = new Date();
        SimpleDateFormat currentTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update = currentTime.format(date);
        Date updateDate = dateFormat.parse(update);
        report.setUpdatedDate(updateDate);
        return report;
    }

    /*
     *レコード削除
     */
    public void deleteReport(Integer id) {
        Report deleteReport = deleteReportEntity(id);
        reportRepository.delete(deleteReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report deleteReportEntity(Integer id) {
        Report report = new Report();
        report.setId(id);
        return report;
    }

    /*
     * レコード1件取得
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        results.add((Report) reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }
}
