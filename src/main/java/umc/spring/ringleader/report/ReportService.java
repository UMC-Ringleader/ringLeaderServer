package umc.spring.ringleader.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.report.model.CheckingNonconformity;
import umc.spring.ringleader.report.model.PostReportReq;

import static umc.spring.ringleader.config.Constant.RATIO_REPORT_BY_FEEDBACK;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public int createReport(PostReportReq postReportReq){
        int report = reportRepository.createReport(postReportReq);
        return report;
    }

    //true = 신고문 표시     false = 신고문 미표시
    public boolean checkNonconformity(int reviewId) {
        CheckingNonconformity reportedAndFeedbackCount = reportRepository.getReportedAndFeedbackCount(reviewId);
        int reported = reportedAndFeedbackCount.getReportedCount();
        int feedback = reportedAndFeedbackCount.getFeedbackCount();

        if (feedback == 0) {
            return feedback < reported;
        }
        else{
            double result = reported/(double) feedback;
            return result > RATIO_REPORT_BY_FEEDBACK;
        }
    }

}
