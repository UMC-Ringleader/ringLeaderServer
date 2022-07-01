package umc.spring.ringleader.contribution1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.contribution1.model.ContributionRanking;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;
import umc.spring.ringleader.contribution1.model.ContributionWithNickNameByReviewId;

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

}
