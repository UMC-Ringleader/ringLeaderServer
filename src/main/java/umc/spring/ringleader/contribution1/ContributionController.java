package umc.spring.ringleader.contribution1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;
import umc.spring.ringleader.contribution1.model.ContributionWithNickNameByReviewId;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contributions")
public class ContributionController {

    private final ContributionService service;

    @Autowired
    public ContributionController(ContributionService service) {
        this.service = service;
    }

}
