package umc.spring.ringleader.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.search.model.*;
import umc.spring.ringleader.search.model.GetSearchListRes;
import umc.spring.ringleader.search.model.PostSearchResultReq;
import umc.spring.ringleader.search.model.SearchTmp;


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

    public List<GetSearchListRes> findAllSavedList(int regionId) {
        String query = "SELECT*\n" +
                "FROM RegionReviewDetails\n" +
                "WHERE regionId = ?" +
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

    public List<SearchTmp> getReviews(String searchWord) {
        String getSearchReviewsQuery = "Select *\n"
                + "From Review as R, User as U, UserRegionContribution as URC\n"
                + "WHERE U.userId = R.userId\n"
                + "AND URC.regionId = R.regionId\n"
                + "AND URC.userId = R.userId\n"
                + "AND(R.title LIKE concat('%',?,'%')\n"
                + "OR R.hashtag1 LIKE concat('%',?,'%')\n"
                + "OR R.hashtag2 LIKE concat('%',?,'%')\n"
                + "OR R.hashtag3 LIKE concat('%',?,'%'))\n"
                + "ORDER BY R.created_at DESC;";
        return this.jdbcTemplate.query(getSearchReviewsQuery,
                (rs, rowNum) -> new SearchTmp(
                        rs.getInt("userId"),
                        rs.getString("nickName"),
                        rs.getInt("contribution"),
                        rs.getInt("reviewId"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("hashtag1"),
                        rs.getString("hashtag2"),
                        rs.getString("hashtag3"),
                        rs.getString("contents")
                ), searchWord,searchWord, searchWord, searchWord);
    }
}
