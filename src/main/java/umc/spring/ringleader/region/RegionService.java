package umc.spring.ringleader.region;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.spring.ringleader.region.dto.GetRegionListRes;
import umc.spring.ringleader.region.dto.GetRegionRes;
import umc.spring.ringleader.region.dto.GetRegionTotalRes;
import umc.spring.ringleader.region.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionDao regionDao;

    // 지역 세부정보 조회
    public GetRegionRes getRegionDetail(int regionId) {
        log.info("[Region][Service] : 지역 세부사항");
        return regionDao.getRegionDetail(regionId);
    }

    // 지역 list up
    public GetRegionTotalRes getRegionList(int userId) {
        log.info("[Region][Service] : 지역 전체조회");
        GetRegionTotalRes result = new GetRegionTotalRes(
                regionDao.getRegionRecent(userId),
                regionDao.getRegionList(userId)
        );
        return result;
    }
}
