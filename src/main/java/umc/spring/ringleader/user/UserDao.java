package umc.spring.ringleader.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.contribution.model.ContributionWithLocation;
import umc.spring.ringleader.region.dto.GetRegionRes;
import umc.spring.ringleader.user.dto.GetUserRes;
import umc.spring.ringleader.user.dto.GetUserTemp;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetUserTemp getUserDetail(int userId) {

        // userId 로 이미지, 닉네임 조회
        String getRegionQuery = "select * from User where userId = ?";
        int getRegionParams = userId;
        return this.jdbcTemplate.queryForObject(getRegionQuery,
                (rs, rowNum) -> new GetUserTemp(
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getString("image")),
                getRegionParams);
    }
}
