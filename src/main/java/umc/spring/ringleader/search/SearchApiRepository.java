package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.search.model.PostSearchResultReq;

import javax.sql.DataSource;

@Repository
public class SearchApiRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SearchApiRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updateSearchedResult(int reviewId, int regionId, String title, String category, String address, String roadAddress, int mapx, int mapy) {
        String query = "insert into RegionReviewDetails(reviewId, regionId, title, category, address, roadAddress, mapx, mapy)\n" +
                "VALUES (?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{reviewId, regionId, title, category, address, roadAddress, mapx, mapy};
        jdbcTemplate.update(query, params);
    }
}
