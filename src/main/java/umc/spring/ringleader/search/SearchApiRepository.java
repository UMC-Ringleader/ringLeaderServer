package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.review.model.dto.SearchTmp;


import javax.sql.DataSource;
import java.util.List;

@Repository
public class SearchApiRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SearchApiRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int updateSearchedResult(String title, String category, String address, String roadAddress, int mapx, int mapy, int regionId) {
        String query = "insert into RegionReviewDetails(title, category, address, roadAddress, mapx, mapy,regionId)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        Object[] params = new Object[]{title, category, address, roadAddress, mapx, mapy, regionId};
        jdbcTemplate.update(query, params);
        return jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    public int updateSearchedResultList(PostSearchResultReq postSearchResultReq,int regionId) {
        return updateSearchedResult((postSearchResultReq.getTitle().replace("<b>","").replace("</b>","")), postSearchResultReq.getCategory(),
                postSearchResultReq.getAddress(), postSearchResultReq.getRoadAddress(), postSearchResultReq.getMapx(), postSearchResultReq.getMapy(),regionId);
    }

    public int findRegionId(String regionForSearch) {
        String query = "SELECT regionId FROM Region where regionForSearch = ?";
        String param = regionForSearch;
        return jdbcTemplate.queryForObject(query, int.class, param);
    }

    public List<GetSearchListRes> findAllSavedList(int regionId) {
        String query = "SELECT*\n" +
                "FROM RegionReviewDetails\n" +
                "WHERE regionId = ?\n" +
                "ORDER BY created_at desc";
        int param = regionId;
        return jdbcTemplate.query(query,
                (rs, rowNum) -> new GetSearchListRes(
                        rs.getInt("RRDId"),
                        rs.getString("title"),
                        rs.getString("address")
                ), param
        );
    }

    public List<GetSearchListRes> findByTitle(String keyword) {
//        String query = "SELECT*\n" +
//                "FROM RegionReviewDetails\n" +
//                "WHERE title LIKE '%${keyword}%'\n" +
//                "ORDER BY created_at desc;\n";
        String query = "SELECT*\n" +
                "FROM RegionReviewDetails\n" +
                "WHERE title LIKE ?\n" +
                "ORDER BY created_at desc;\n";
        String param = "%"+keyword+"%";

        return jdbcTemplate.query(query,
                (rs, rowNum) -> new GetSearchListRes(
                        rs.getInt("RRDId"),
                        rs.getString("title"),
                        rs.getString("address")
                )
                , param);
    }

    public GetSearchListRes findByTitleAndAddress(String title, String address) {
        String query = "SELECT*\n" +
                "FROM RegionReviewDetails\n" +
                "WHERE title =? AND address =?";
        Object[] params = {title, address};
        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetSearchListRes(
                        rs.getInt("RRDId"),
                        rs.getString("title"),
                        rs.getString("address")
                )
                ,params);
    }

    public int checkingExistingWithTitleAndAddress(String title, String address) {
        String query = "SELECT EXISTS(\n" +
                "    SELECT * FROM RegionReviewDetails\n" +
                "    WHERe title=? and address =?\n" +
                "    )";

        Object[] params = {title, address};

        return jdbcTemplate.queryForObject(query, int.class, params);
    }

}
