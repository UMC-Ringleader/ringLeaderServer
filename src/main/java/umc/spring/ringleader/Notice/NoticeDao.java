package umc.spring.ringleader.notice1;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import umc.spring.ringleader.notice1.DTO.NoticeRes;
import umc.spring.ringleader.notice1.DTO.PostNoticeReq;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoticeDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // Notice 등록
    public int saveNotice(PostNoticeReq req) {
        String createNoticeQuery = "insert into Notice ("
                + "managerId, title, content"
                + ") VALUES (?,?,?)";
        Object[] createNoticeParams = new Object[] {
                req.getManagerId(),
                req.getTitle(),
                req.getContent()

        };
        this.jdbcTemplate.update(createNoticeQuery, createNoticeParams); // error

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 Row의 id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,
                Integer.class);
    }

    // Notice 조회
    public List<NoticeRes> getNotices() {
        String getNoticesQuery = "Select *\n"
                + "    From Notice as N\n"
                + "    ORDER BY N.noticeId DESC;";

        return this.jdbcTemplate.query(getNoticesQuery,new NoticeMapper()); // error
    }


    private static class NoticeMapper implements RowMapper<NoticeRes> {
        @Override
        public NoticeRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new NoticeRes(
                    rs.getInt("managerId"),
                    rs.getString("title"),
                    rs.getString("content")
            );
        }
    }
}