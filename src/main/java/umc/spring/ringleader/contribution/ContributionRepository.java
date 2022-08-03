package umc.spring.ringleader.contribution;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.contribution.model.ContributionRanking;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.contribution.model.ContributionWithNickNameByReviewId;
import umc.spring.ringleader.region.dto.GetRegionListRes;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContributionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContributionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ContributionWithLocation> getContributionWithLocationByUserId(int userId){
        String query = "select r.placeName, urc.contribution\n" +
                "FROM UserRegionContribution as urc\n" +
                "        join (select userId, nickName\n" +
                "                from User u) u on u.userId = urc.userId\n" +
                "        join(select regionId,placeName\n" +
                "                from Region r) r on r.regionId = urc.regionId\n" +
                "WHERE urc.userId = ?\n" +
                "ORDER BY urc.contribution desc\n" +
                "limit 3;";

        int param = userId;

        return jdbcTemplate.query(query,
                (rs, rowNum) -> new ContributionWithLocation(
                        rs.getString("placeName"),
                        rs.getInt("contribution")
                )
                , param);
    }


    public ContributionWithNickNameByReviewId getCWNBR(int reviewId){
        String query = "select u.nickName,urc.contribution\n" +
                "FROM Review as r\n" +
                "    join(select userId,nickName\n" +
                "from User as u) u on u.userId = r.userId\n" +
                "    join(select userId,regionId,contribution\n" +
                "from UserRegionContribution as urc) urc on urc.userId = r.userId and urc.regionId = r.regionId\n" +
                "WHERE reviewId = ?";

        int param = reviewId;
        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new ContributionWithNickNameByReviewId(
                        rs.getString("nickName"),
                        rs.getInt("contribution")
                )
                , param);

    }

    public List<ContributionRanking> getRankingByRegionId(int regionId){
        String query = "select @rownum := @rownum + 1 AS ranking,\n" +
                "     u.userId,u.nickName, urc.contribution\n" +
                "FROM Region as r\n" +
                "         join(select userId, regionId, contribution\n" +
                "              FROM UserRegionContribution as urc) urc on urc.regionId = r.regionId\n" +
                "         join(select userId, nickName\n" +
                "              FROM User as u) u on u.userId = urc.userId\n" +
                "        join(SELECT @rownum :=0) TMP\n" +
                "WHERE r.regionId = ?\n" +
                "ORDER BY urc.contribution desc ;";
        int param = regionId;
        return jdbcTemplate.query(query,
                (rs, rowNum) -> new ContributionRanking(
                        rs.getInt("ranking"),
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getInt("contribution")
                )
                , param);
    }

    public int getContribution(int userId, int regionId) {
        String query = "select contribution\n" +
                "FROM UserRegionContribution\n" +
                "WHERE userId= ? and regionId =?;";

        Object[] params = new Object[]{userId, regionId};
        return jdbcTemplate.queryForObject(query,int.class, params);
    }

    /**
     * 기여도 조작 메서드
     * @param userId
     * @param regionId
     * @param n (가감 상수)
     * @return
     */
    public int updateContribution(int userId, int regionId, int n){
        String query = "select contribution\n" +
                "FROM UserRegionContribution\n" +
                "WHERE userId= ? and regionId =?;";

        Object[] params = new Object[]{userId, regionId};
        int contribution = jdbcTemplate.queryForObject(query, int.class,params);

        contribution +=n;

        String queryForUpdate = "UPDATE UserRegionContribution SET contribution = ? WHERE userId = ? and regionId=?;";
        Object[] paramsForUpdate = new Object[]{contribution, userId, regionId};
        return this.jdbcTemplate.update(queryForUpdate , paramsForUpdate);
    }

    public void initializeUserRegionContribution(int userId, List<GetRegionListRes> regionList) {
        for (GetRegionListRes region : regionList) {
            String initUserContribution = "insert into UserRegionContribution ("
                    + "userId, regionId, contribution"
                    + ") VALUES (?,?,?)"; // 실행될 동적 쿼리문
            Object[] initUserContributionParam = new Object[]{
                    userId,
                    region.getRegionId(),
                    0
            };

            this.jdbcTemplate.update(initUserContribution , initUserContributionParam);
        }
    }

    //accessed 초기화
    public void initializeAccessedToFalse() {
        String query = "UPDATE UserRegionContribution SET accessed = ?";
        boolean param = false;

        this.jdbcTemplate.update(query, param);
    }

    //accessed가 true 인지 false 인지 반환하여 확인
    public boolean getAccessed(int userId, int regionId) {
        String query = "select accessed\n" +
                "FROM UserRegionContribution\n" +
                "WHERE userId= ? and regionId =?;";

        Object[] params = new Object[]{userId, regionId};
        return jdbcTemplate.queryForObject(query,boolean.class, params);
    }

    //accessed를 true 로 바꾸는 메소드
    public void updateAccessed(int userId, int regionId) {
        String query = "update UserRegionContribution set accessed = true where userId=? and regionId =?";
        Object[] params = {userId, regionId};
        jdbcTemplate.update(query, params);
    }
}
