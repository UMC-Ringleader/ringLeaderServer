package umc.spring.ringleader.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReportReq {

    public PostReportReq() {
    }

    private String reportedContent;
    private int reviewViewerId;
    private int reviewId;

}
