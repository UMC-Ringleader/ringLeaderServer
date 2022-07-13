package umc.spring.ringleader.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.report.model.CheckingNonconformity;
import umc.spring.ringleader.report.model.PostReportReq;

import javax.sql.DataSource;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public int createReport(PostReportReq p) {
        String query = "INSERT INTO Report(reviewViewerId,reviewId,reportedContent) VALUES(?,?,?)";
        Object[] params = {p.getReviewViewerId(), p.getReviewId(), p.getReportedContent()};

        return jdbcTemplate.update(query, params);
    }

    public CheckingNonconformity getReportedAndFeedbackCount(int reviewId) {
        String query = "SELECT COUNT(ReportId) as reportedCount,\n" +
                "       IF(feedbackCount is null,0,feedbackCount) as feedbackCount\n" +
                "FROM Report as r\n" +
                "    LEFT JOIN(\n" +
                "        SELECT reviewId,COUNT(rfId) as feedbackCount\n" +
                "        FROM reviewFeedback\n" +
                "        GROUP BY reviewId) r1 on r1.reviewId= r.reviewId\n" +
                "WHERE r.reviewId = ?;";
        int param = reviewId;

        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new CheckingNonconformity(
                        rs.getInt("reportedCount"),
                        rs.getInt("feedbackCount")
                )
                , param);
    }
}
