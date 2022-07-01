package umc.spring.ringleader.region;

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

@Repository
public class RegionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 한개 지역 조회 (상세페이지)
    public GetRegionRes getRegionDetail(int regionId) {
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
        String getUserQuery = "select * from User where userId = ?";
        int getUserParams = userId;
        UserInfoDTO userInfoDTO = this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new UserInfoDTO(
                        rs.getInt("userId"),
                        rs.getInt("lastVisitRegionId")),
                getUserParams);
        if (userInfoDTO.getLastVisitRegionId()!=0) {
            String getRegionQuery = "select * from Region where regionId = ?";
            int getRegionParams = userInfoDTO.getLastVisitRegionId();
            return this.jdbcTemplate.queryForObject(getRegionQuery,
                    (rs, rowNum) -> new GetRegionListRes(
                            rs.getInt("regionId"),
                            rs.getString("placeName")),
                    getRegionParams);
        }
        else {
            return null;
        }

    }

    // 지역 리스트업 (전체) (regionId, placeName 반환)
    public List<GetRegionListRes> getRegionList(int userId) {
        String getUserQuery = "select * from User where userId = ?";
        int getUserParams = userId;
        UserInfoDTO userInfoDTO = this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new UserInfoDTO(
                        rs.getInt("userId"),
                        rs.getInt("lastVisitRegionId")),
                getUserParams);
        int lastVisitRegion = userInfoDTO.getLastVisitRegionId();
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
