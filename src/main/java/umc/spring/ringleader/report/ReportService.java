package umc.spring.ringleader.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.report.model.CheckingNonconformity;
import umc.spring.ringleader.report.model.PostReportReq;
import umc.spring.ringleader.report.model.PostReportSuggestionRes;
import umc.spring.ringleader.report.model.Report;

import java.util.*;

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

    //신고 제안
    public String reportSuggestion(int reviewId) {
        PostReportSuggestionRes postReportSuggestionRes
                = new PostReportSuggestionRes();
        List<String> reportedContents = reportRepository.getReportSuggestion(reviewId);
        Report reportList = new Report();
        List<String> reportingContents = reportList.getReportingContents();
        Map<String, Integer> map = new HashMap<>();
        for (String s : reportingContents) {
            int frequency = Collections.frequency(reportedContents, s);
            map.put(s, frequency);
        }

        Optional<Integer> max = map.values().stream()
                .max(Comparator.comparing(x -> x));

        String result = "";
        for (String s : reportingContents) {
            if (Objects.equals(map.get(s), max.get())) {
                result = s;
                break;
            }
        }
        return result;

    }

}
