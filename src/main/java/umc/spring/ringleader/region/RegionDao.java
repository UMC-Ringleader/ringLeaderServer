package umc.spring.ringleader.region;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.region.dto.GetRegionListRes;
import umc.spring.ringleader.region.dto.GetRegionRes;
import umc.spring.ringleader.region.dto.UserInfoDTO;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

@Slf4j
@Repository
public class RegionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 한개 지역 조회 (상세페이지)
    public GetRegionRes getRegionDetail(int regionId) {
//        log.info("[Region][DAO] : 지역 세부사항");

        // regionId 로 세부사항 조회
        String getRegionQuery = "select * from Region where regionId = ?";
        int getRegionParams = regionId;
        return this.jdbcTemplate.queryForObject(getRegionQuery,
                (rs, rowNum) -> new GetRegionRes(
                        rs.getInt("regionId"),
                        rs.getString("placeName"),
                        rs.getString("image"),
                        rs.getString("location"),
                        rs.getString("regionInfo")),
                getRegionParams);
    }

    // 최근 방문 지역 조회 (userId로)
    public GetRegionListRes getRegionRecent(int userId) {
//        log.info("[Region][DAO] : 최근 방문 지역 조회");
        String getUserQuery = "select * from User where userId = ?";
        int getUserParams = userId;
        UserInfoDTO userInfoDTO = this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new UserInfoDTO(
                        rs.getInt("userId"),
                        rs.getInt("lastVisitRegionId")),
                getUserParams);
        if (userInfoDTO.getLastVisitRegionId()!=0) { // 최근 방문지역 있으면 그 지역 반환
            String getRegionQuery = "select * from Region where regionId = ?";
            int getRegionParams = userInfoDTO.getLastVisitRegionId();
            return this.jdbcTemplate.queryForObject(getRegionQuery,
                    (rs, rowNum) -> new GetRegionListRes(
                            rs.getInt("regionId"),
                            rs.getString("placeName")),
                    getRegionParams);
        }
        else { // 없으면 null
            return null;
        }

    }

    // 지역 리스트업 (전체) (regionId, placeName 반환)
    public List<GetRegionListRes> getRegionList(int userId) {
//        log.info("[Region][DAO] : 지역 전체 조회");

        // userId 로 최근 방문 지역 조회
        String getUserQuery = "select * from User where userId = ?";
        int getUserParams = userId;
        UserInfoDTO userInfoDTO = this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new UserInfoDTO(
                        rs.getInt("userId"),
                        rs.getInt("lastVisitRegionId")),
                getUserParams);
        int lastVisitRegion = userInfoDTO.getLastVisitRegionId();


        // 조회한 최근 방문 지역을 제외한 지역 리스트
        String getRegionQuery = "select * from Region";
        List<GetRegionListRes> rawList = this.jdbcTemplate.query(getRegionQuery,
                (rs, rowNum) -> new GetRegionListRes(
                        rs.getInt("regionId"),
                        rs.getString("placeName")));

        List<GetRegionListRes> result = new ArrayList<>();
        for (GetRegionListRes getRegionListRes : rawList) {
            if (getRegionListRes.getRegionId()!=lastVisitRegion) {
                result.add(getRegionListRes);
            }
        }
        return result;
        }


    }
