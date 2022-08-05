package umc.spring.ringleader.region;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.region.dto.GetRegionListRes;
import umc.spring.ringleader.region.dto.GetRegionRes;
import umc.spring.ringleader.region.dto.GetRegionTotalRes;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    /**
     * Region 전체조회
     * 최근 방문지역 1개 (최초방문시 해당 x 예외처리 완)
     * 나머지 지역 전체
     * 따로따로 담아서 보냄
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/list")
    @ApiOperation(value = "User 별 Region 리스트업")
    @ApiImplicitParam(name = "userId", required = true)
    public BaseResponse<GetRegionTotalRes> getRegionList(@PathVariable("userId") int userId) {
        log.info("[Region][GET] : Region 전체조회 / userId = {}", userId);
        GetRegionTotalRes getRegionTotalRes = regionService.getRegionList(userId);
        return new BaseResponse<>(getRegionTotalRes);
    }


    /**
     * Region 세부사항
     * @param regionId
     * @return
     */
    @GetMapping("/{regionId}/detail")
    @ApiOperation(value = "Region 세부사항")
    @ApiImplicitParam(name = "regionId", required = true)
    public BaseResponse<GetRegionRes> getRegionDetail(@PathVariable("regionId") int regionId) {
        log.info("[Region][GET] : Region 세부사항 / regionId = {}", regionId);
        GetRegionRes getRegionRes = regionService.getRegionDetail(regionId);
        return new BaseResponse<>(getRegionRes);
    }

    @GetMapping("/ranking/list")
    @ApiOperation(value = "Region 활성도 랭킹조회")
    public BaseResponse<List<GetRegionListRes>> getRegionListOrderByActivity() {
        log.info("[Region][GET] : Region 활성도 랭킹");
        List<GetRegionListRes> regionOrderByActivity = regionService.getRegionOrderByActivity();
        return new BaseResponse<>(regionOrderByActivity);
    }

//    @GetMapping("/update")
//    public void updateRegionActivity() {
//        regionService.updateRegionActivity();
//    }


}
