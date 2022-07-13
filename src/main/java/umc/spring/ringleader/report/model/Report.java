package umc.spring.ringleader.report.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static umc.spring.ringleader.config.Constant.*;

@Getter
public class Report {

    private final List<String> reportingContents
            = Arrays.asList(UNRELIABLE_COMMENT_MESSAGE,
            SUSPICIOUS_ADVERTISING_MESSAGE,
            IMPROPER_MESSAGE,
            EXCESSIVE_PUBLICITY_MESSAGE,
            INSINCERE_WRITING_MESSAGE,
            INSINCERE_WRITING_MESSAGE,
            INSINCERE_WRITING_MESSAGE,
            DUPLICATE_REVIEWS_MESSAGE,
            INACCURATE_ADDRESS_MESSAGE);



}
