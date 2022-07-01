package umc.spring.ringleader.region;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import umc.spring.ringleader.config.BaseException;
import umc.spring.ringleader.config.BaseResponse;
import umc.spring.ringleader.region.dto.GetRegionListRes;
import umc.spring.ringleader.region.dto.GetRegionRes;
import umc.spring.ringleader.region.dto.GetRegionTotalRes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;



    @ResponseBody
    @GetMapping("/{userId}/list")
    public BaseResponse<GetRegionTotalRes> getRegionList(@PathVariable("userId") int userId) {
        log.info("[Region][Controller] : 지역 전체조회");
        GetRegionTotalRes getRegionTotalRes = regionService.getRegionList(userId);
        return new BaseResponse<>(getRegionTotalRes);
    }


    @ResponseBody
    @GetMapping("/{regionId}/detail")
    public BaseResponse<GetRegionRes> getRegionDetail(@PathVariable("regionId") int regionId) {
        log.info("[Region][Controller] : 지역 세부사항");
        GetRegionRes getRegionRes = regionService.getRegionDetail(regionId);
        return new BaseResponse<>(getRegionRes);

    }



}
