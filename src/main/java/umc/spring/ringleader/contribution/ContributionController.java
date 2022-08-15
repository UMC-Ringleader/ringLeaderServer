package umc.spring.ringleader.contribution;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.contribution.model.ContributionRanking;

import java.util.List;

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
    @ApiOperation(value = "Region별 기여도 순위 조회")
    @ApiImplicitParam(name = "regionId" ,value = "지역 식별자", required = true)
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
    @ApiOperation(value = "User의 개인 랭킹 조회")
    @ApiImplicitParam(name = "userId" ,value = "유저 식별자", required = true)
    @GetMapping("/ranking/{userId}")
    public BaseResponse<ContributionRanking> getUserRanking(@RequestParam int regionId, @PathVariable int userId) {
        log.info("[Contribution][GET] : User 개인 랭킹조회 api / regionId = {}, userId = {}", regionId, userId);
        ContributionRanking result = service.getRankingByUserId(userId, regionId).get();

        return new BaseResponse<>(result);

    }
}
