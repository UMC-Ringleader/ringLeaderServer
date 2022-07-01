package umc.spring.ringleader.contribution1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.contribution1.model.ContributionWithLocation;

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
}
