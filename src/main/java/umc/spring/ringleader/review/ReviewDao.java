package umc.spring.ringleader.review;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import umc.spring.ringleader.review.model.dto.PostImage;
import umc.spring.ringleader.review.model.dto.PostReviewReq;
import umc.spring.ringleader.review.model.dto.ReviewImgUrl;
import umc.spring.ringleader.review.model.dto.ReviewTmp;

@Repository
public class ReviewDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int saveReview(PostReviewReq req) {
		String createReviewQuery = "insert into Review ("
			+ "title, category, hashtag1, hashtag2, hashtag3, regionId, contents, userId"
			+ ") VALUES (?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
		Object[] createReviewParams = new Object[]{
			req.getTitle(),
			req.getCategory(),
			req.getHashtag1(),
			req.getHashtag2(),
			req.getHashtag3(),
			req.getRegionId(),
			req.getContents(),
			req.getUserId()
		}; // 동적 쿼리의 ?부분에 주입될 값
		this.jdbcTemplate.update(createReviewQuery, createReviewParams);

		String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값을 가져온다.
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
	}

	public int insertImages(int savedId, PostImage p) {
		Object[] insertImgParam = {savedId, p.getImage()};
		String insertImgQuery = "INSERT INTO reviewImgs(reviewId,image) VALUES(?,?)";
		return jdbcTemplate.update(insertImgQuery, insertImgParam);

	}


	public int deleteReview(int reviewId) {
		String deleteUserQuery = "delete from Review where reviewId = ?"; // 해당 userIdx를 만족하는 유저를 삭제하는 쿼리문
		Object[] deleteIdx = new Object[] {reviewId};
		return this.jdbcTemplate.update(deleteUserQuery, deleteIdx); //쿼리 요청(삭제했으면 1, 실패했으면 0)
	}

	public List<ReviewTmp> getReviewsByRegion(int regionId) {
		String getReviewsQuery = "select * from Review where regionId = ?";
		return this.jdbcTemplate.query(getReviewsQuery,
			(rs, rowNum) -> new ReviewTmp(
				rs.getInt("reviewId"),
				rs.getString("title"),
				rs.getString("category"),
				rs.getString("hashtag1"),
				rs.getString("hashtag2"),
				rs.getString("hashtag3"),
				rs.getString("contents"),
				rs.getInt("userId")// RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
			), regionId);
	}

	public void updateLastVisitedRegion(int userId, int regionId) {
		String modifyUserNameQuery = "update User set lastVisitRegionId = ? where userId = ?";
		Object[] modifyUserNameParams = new Object[]{regionId, userId};
		this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
	}

	public List<ReviewTmp> getReviewsByCategory(String category, int regionId) {
		String getReviewsQuery = "select * from Review where regionId = ? AND category = ?";
		return this.jdbcTemplate.query(getReviewsQuery,
			(rs, rowNum) -> new ReviewTmp(
				rs.getInt("reviewId"),
				rs.getString("title"),
				rs.getString("category"),
				rs.getString("hashtag1"),
				rs.getString("hashtag2"),
				rs.getString("hashtag3"),
				rs.getString("contents"),
				rs.getInt("userId")
			), regionId, category);
	}

	public List<ReviewTmp> getUserReviewsByRegionId(int userId, int regionId) {
		String getReviewsQuery = "select * from Review where userId = ? AND regionId = ?";
		return this.jdbcTemplate.query(getReviewsQuery,
			(rs, rowNum) -> new ReviewTmp(
				rs.getInt("reviewId"),
				rs.getString("title"),
				rs.getString("category"),
				rs.getString("hashtag1"),
				rs.getString("hashtag2"),
				rs.getString("hashtag3"),
				rs.getString("contents"),
				rs.getInt("userId")
			), userId, regionId);
	}


	/*
	Login / User 부분에 어울림

	public String getUserName(int userId) {
		String findUserName = "select nickName from User where userId = ?";
		return this.jdbcTemplate.queryForObject(findUserName, String.class, userId);
	}
	 */

	/*
	임시로 사용
	Contribution에 더 어울림

	public int getContributionByRegion(int userId, int regionId) {
		String findUserName = "select contribution from UserRegionContribution where userId = ? AND regionId = ?";
		return this.jdbcTemplate.queryForObject(findUserName, Integer.class, userId, regionId);
	}
 	*/

	public List<String> getReviewImgs(int reviewId) {
		String getReviewImgs = "select image from reviewImgs where reviewId = ?";
		return this.jdbcTemplate.queryForList(getReviewImgs, String.class, reviewId);
	}

}
