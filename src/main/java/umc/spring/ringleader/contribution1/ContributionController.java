package umc.spring.ringleader.contribution1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.contribution1.model.ContributionRanking;
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

    //랭킹 조회 api
    //ex) localhost:8100/contributions/ranking?regionId=2
    @GetMapping("/ranking")
    public BaseResponse<List<ContributionRanking>> getRankingByReviewId(@RequestParam int regionId) {
        return new BaseResponse<>(service.getRankingByRegionId(regionId));
    }

    //개인 랭킹조회 api
    //ip:port/contribution/ranking/{userIdx}?/?regionId=?
    //ex) localhost:8100/contributions/ranking/1?regionId=2
    @GetMapping("/ranking/{userId}")
    public BaseResponse<ContributionRanking> getUserRanking(@RequestParam int regionId, @PathVariable int userId) {
        ContributionRanking result = service.getRankingByUserId(userId, regionId).get();
        /*if (result.equals(null)) {
            return new BaseException();
        }
        else{*/
            return new BaseResponse<>(result);

    }
}
