package umc.spring.ringleader.report.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckingNonconformity {

    public CheckingNonconformity() {
    }

    private int reportedCount;
    private int feedbackCount;
}
