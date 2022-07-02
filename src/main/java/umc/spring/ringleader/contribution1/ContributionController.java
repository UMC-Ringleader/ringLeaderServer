package umc.spring.ringleader.contribution1;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/contributions")
public class ContributionController {

    private final ContributionService service;

    @Autowired
    public ContributionController(ContributionService service) {
        this.service = service;
    }

    /**
     * 랭킹 조회 api
     * @param regionId
     * @return
     */
    //ex) localhost:8100/contributions/ranking?regionId=2
    @GetMapping("/ranking")
    public BaseResponse<List<ContributionRanking>> getRankingByRegionId(@RequestParam int regionId) {
        log.info("[Contribution][GET] : Region별 랭킹조회 api / regionId = {}", regionId);
        return new BaseResponse<>(service.getRankingByRegionId(regionId));
    }

    /**
     * 개인 랭킹조회 api
     * @param regionId
     * @param userId
     * @return
     */
    //ip:port/contribution/ranking/{userIdx}?/?regionId=?
    //ex) localhost:8100/contributions/ranking/1?regionId=2
    @GetMapping("/ranking/{userId}")
    public BaseResponse<ContributionRanking> getUserRanking(@RequestParam int regionId, @PathVariable int userId) {
        log.info("[Contribution][GET] : User 개인 랭킹조회 api / regionId = {}, userId = {}", regionId, userId);
        ContributionRanking result = service.getRankingByUserId(userId, regionId).get();
        /*if (result.equals(null)) {
            return new BaseException();
        }
        else{*/
            return new BaseResponse<>(result);

    }
}
