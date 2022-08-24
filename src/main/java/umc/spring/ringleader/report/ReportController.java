package umc.spring.ringleader.report;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "신고 사유 목록 조회")
    @GetMapping("/lists")
    public BaseResponse<List<String>> getReportingList() {
        Report report = new Report();
        List<String> reportingContents = report.getReportingContents();

        return new BaseResponse<>(reportingContents);
    }

    @ApiOperation(value = "신고 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportedContent" ,value = "신고 사유", required = true),
            @ApiImplicitParam(name = "reviewViewerId" ,value = "로그인 된 유저 식별자", required = true),
            @ApiImplicitParam(name = "reviewId" ,value = "Review 식별자", required = true)
    })
    @PostMapping("")
    public BaseResponse<String> createReport(@RequestBody PostReportReq postReportReq) {
        int reportResult = reportService.createReport(postReportReq);
        String reportComplete = "신고가 완료되었습니다.";
        return new BaseResponse<>(reportComplete);
    }
}
