package umc.spring.ringleader.review;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import umc.spring.ringleader.review.model.dto.PostReviewReq;

@Repository
public class ReviewDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int saveReview(PostReviewReq req) {
		String createReviewQuery = "insert into Review ("
			+ "title, category, hashtag1, hashtag2, hashtag3, regionId, contents"
			+ ") VALUES (?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
		Object[] createReviewParams = new Object[]{
			req.getTitle(),
			req.getCategory(),
			req.getHashtag1(),
			req.getHashtag2(),
			req.getHashtag3(),
			req.getRegionId(),
			req.getContents()
		}; // 동적 쿼리의 ?부분에 주입될 값
		this.jdbcTemplate.update(createReviewQuery, createReviewParams);

		String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값을 가져온다.
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
	}

}
