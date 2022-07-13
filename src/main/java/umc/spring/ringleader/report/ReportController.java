package umc.spring.ringleader.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.report.model.PostReportReq;
import umc.spring.ringleader.report.model.Report;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/lists")
    public BaseResponse<List<String>> getReportingList() {
        Report report = new Report();
        List<String> reportingContents = report.getReportingContents();

        return new BaseResponse<>(reportingContents);
    }

    @PostMapping("")
    public BaseResponse<String> createReport(@RequestBody PostReportReq postReportReq) {
        int reportResult = reportService.createReport(postReportReq);
        String reportComplete = "신고가 완료되었습니다.";
        return new BaseResponse<>(reportComplete);
    }
}
