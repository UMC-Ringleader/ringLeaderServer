package umc.spring.ringleader.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReportSuggestionRes {
    public PostReportSuggestionRes() {

    }

    private int reviewId;
    private String reportedContent;
}
