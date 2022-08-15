package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SearchApiRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SearchApiRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updateSearchedResult(String title, String category, String address, String roadAddress, int mapx, int mapy, int regionId) {
        String query = "insert into RegionReviewDetails(title, category, address, roadAddress, mapx, mapy,regionId)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        Object[] params = new Object[]{title, category, address, roadAddress, mapx, mapy, regionId};
        jdbcTemplate.update(query, params);
    }

    public void updateSearchedResultList(PostSearchResultReq postSearchResultReq,int regionId) {
        updateSearchedResult((postSearchResultReq.getTitle().replace("<b>","").replace("</b>","")), postSearchResultReq.getCategory(),
                postSearchResultReq.getAddress(), postSearchResultReq.getRoadAddress(), postSearchResultReq.getMapx(), postSearchResultReq.getMapy(),regionId);
    }

    public int findRegionId(String regionForSearch) {
        String query = "SELECT regionId FROM Region where regionForSearch = ?";
        String param = regionForSearch;
        return jdbcTemplate.queryForObject(query, int.class, param);
    }

    public List<GetSearchListRes> findAllSavedList() {
        String query = "SELECT * FROM RegionReviewDetails ";
        return jdbcTemplate.query(query,
                (rs, rowNum) -> new GetSearchListRes(
                        rs.getInt("RRDId"),
                        rs.getString("title"),
                        rs.getString("address")
                )
        );
    }
}
