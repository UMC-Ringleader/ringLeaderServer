package umc.spring.ringleader.review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import umc.spring.ringleader.review.model.dto.*;

@Repository
public class ReviewDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int saveReviewForThreeHashTags(PostReviewReq req) {
		String createReviewQuery = "insert into Review ("
			+ "title, category, hashtag1, hashtag2, hashtag3, regionId, contents, userId"
			+ ") VALUES (?,?,?,?,?,?,?,?)";
		Object[] createReviewParams = new Object[] {
			req.getTitle(),
			req.getCategory(),
			req.getHashtags().get(0),
			req.getHashtags().get(1),
			req.getHashtags().get(2),
			req.getRegionId(),
			req.getContents(),
			req.getUserId()
		}; // 동적 쿼리의 ?부분에 주입될 값
		this.jdbcTemplate.update(createReviewQuery, createReviewParams);

		String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 Row의 id값을 가져온다.
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery,
			Integer.class);
	}

	public int insertImages(int savedId, PostImage p) {
		Object[] insertImgParam = {savedId, p.getImage()};
		String insertImgQuery = "INSERT INTO reviewImgs(reviewId,image) VALUES(?,?)";
		return jdbcTemplate.update(insertImgQuery, insertImgParam);
	}

	public void deleteReviewImages(int reviewId) {
		String deleteUserQuery = "delete from reviewImgs where reviewId = ?"; // 해당 reviewId 를 만족하는 이미지를 삭제하는 쿼리문
		Object[] deleteIdx = new Object[] {reviewId};
		this.jdbcTemplate.update(deleteUserQuery, deleteIdx); //쿼리 요청(삭제했으면 1, 실패했으면 0)
	}

	public int deleteReview(int reviewId) {
		String deleteUserQuery = "delete from Review where reviewId = ?"; // 해당 reviewId를 만족하는 리뷰를 삭제하는 쿼리문
		Object[] deleteIdx = new Object[] {reviewId};
		return this.jdbcTemplate.update(deleteUserQuery, deleteIdx); //쿼리 요청(삭제했으면 1, 실패했으면 0)
	}

	public List<ReviewTmp> getReviewsByRegion(int regionId) {
		String getReviewsQuery = "Select *\n"
			+ "    From Review as R\n"
			+ "        Join (Select userId, nickName from User) U on U.userId = R.userId\n"
			+ "        Join (Select userId, contribution, regionId from UserRegionContribution as URC\n"
			+ "            WHERE URC.regionId = ?) URC\n"
			+ "            on URC.userId = R.userId\n"
			+ "    WHERE R.regionId = ?\n"
			+ "    ORDER BY R.created_at DESC;";
		return this.jdbcTemplate.query(getReviewsQuery, new ReviewTmpMapper(), regionId, regionId);
	}

	public void updateLastVisitedRegion(int userId, int regionId) {
		String modifyUserNameQuery = "update User set lastVisitRegionId = ? where userId = ?";
		Object[] modifyUserNameParams = new Object[] {regionId, userId};
		this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
	}

	public List<ReviewTmp> getReviewsByCategory(String category, int regionId) {
		String getReviewsWithCategoryQuery = "Select *\n"
			+ "    From Review as R\n"
			+ "        Join (Select userId, nickName from User) U on U.userId = R.userId\n"
			+ "        Join (Select userId, contribution, regionId from UserRegionContribution as URC\n"
			+ "            WHERE URC.regionId = ?) URC\n"
			+ "            on URC.userId = R.userId\n"
			+ "    WHERE R.regionId = ?\n AND R.category = ?"
			+ "    ORDER BY R.created_at DESC;";
		return this.jdbcTemplate.query(getReviewsWithCategoryQuery,
			new ReviewTmpMapper(), regionId, regionId, category);
	}

	public List<ReviewTmp> getUserReviewsByRegionId(int userId, int regionId) {
		String getUserReviewQuery = "Select *\n"
			+ "    From Review as R\n"
			+ "        Join (Select userId, nickName from User) U on U.userId = R.userId\n"
			+ "        Join (Select userId, contribution, regionId from UserRegionContribution as URC\n"
			+ "            WHERE URC.regionId = ?) URC\n"
			+ "            on URC.userId = R.userId\n"
			+ "    WHERE R.regionId = ?\n AND R.userId = ?"
			+ "    ORDER BY R.created_at DESC;";
		return this.jdbcTemplate.query(getUserReviewQuery,
			new ReviewTmpMapper(), regionId, regionId, userId);
	}

	public List<String> getReviewImgs(int reviewId) {
		String getReviewImgs = "select image from reviewImgs where reviewId = ?";
		return this.jdbcTemplate.queryForList(getReviewImgs, String.class, reviewId);
	}

	// image 있는 리뷰인지 체크
	public boolean isImageExist(int reviewId) {
		String findUserName = "select exists(select * from reviewImgs where reviewId = ?)";
		Integer result = this.jdbcTemplate.queryForObject(findUserName, Integer.class, reviewId); // 있으면 1, 없으면 0
		return result == 1;
	}

	public ReviewSearchTemp getUserRegionIdByReviewId(int reviewId) {
		String getReviewQuery = "select * from Review where reviewId = ?";
		int getReviewParams = reviewId;
		return this.jdbcTemplate.queryForObject(getReviewQuery,
			(rs, rowNum) -> new ReviewSearchTemp(
				rs.getInt("userId"),
				rs.getInt("regionId")),
			getReviewParams);
	}

	public int existingBookmark(int userId, int reviewId) {
		String query = "select EXISTS(select * from UserBookmark where userId = ? and reviewId = ? limit 1) as success;\n";
		Object[] params = {userId, reviewId};

		return jdbcTemplate.queryForObject(query, int.class,params);
	}

	public int createReviewBookmark(int userId, int reviewId) {
		String query = "INSERT INTO UserBookmark(userId,reviewId) VALUES(?,?)";
		Object[] params = {userId, reviewId};

		return jdbcTemplate.update(query, params);
	}

	public int deleteReviewBookmark(int userId, int reviewId) {
		return jdbcTemplate.update("delete from UserBookmark where userId= ? and reviewId =? ", userId, reviewId);
	}

	public ReviewTmp getReviewByReviewId(int reviewId) {
		String query = "select r.*,u.*,urc.*\n" +
                "from Review as r\n" +
                "         join(select*FROM User) u on u.userId = r.userId\n" +
                "         join(select*FROM UserRegionContribution) urc on urc.userId = r.userId and urc.regionId = r.regionId\n" +
                "where r.reviewId = ?;";
		int param = reviewId;
		return jdbcTemplate.queryForObject(query,new ReviewTmpMapper(),param);
	}


	// 인센티브 기여도 판단 대상 review 추출
	public List<ReviewIncentiveTemp> getReviewListToCheckIncentive(LocalDateTime editDateStart, LocalDateTime editDateEnd) {
		String getReviewQuery = "select * from Review where created_at between ? and ?";
		Timestamp getTimeStartParams = Timestamp.valueOf(editDateStart);
		Timestamp getTimeEndParams = Timestamp.valueOf(editDateEnd);
		return this.jdbcTemplate.query(getReviewQuery,
				(rs, rowNum) -> new ReviewIncentiveTemp(
						rs.getInt("reviewId"),
						rs.getInt("userId"),
						rs.getInt("regionId")),
				getTimeStartParams, getTimeEndParams);
	}



	//자주 사용되는 익명 클래스 (new ReviewTmp (rs, rowNum) -> rs.getInt("userId"), ...) 추출하여 재사용
	private static class ReviewTmpMapper implements RowMapper<ReviewTmp> {
		@Override
		public ReviewTmp mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReviewTmp(
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
			);
		}
	}

	public int saveReviewForTwoHashTags(PostReviewReq req) {
		String createReviewQuery = "insert into Review ("
				+ "title, category, hashtag1, hashtag2, regionId, contents, userId"
				+ ") VALUES (?,?,?,?,?,?,?)";
		Object[] createReviewParams = new Object[] {
				req.getTitle(),
				req.getCategory(),
				req.getHashtags().get(0),
				req.getHashtags().get(1),
				req.getRegionId(),
				req.getContents(),
				req.getUserId()
		}; // 동적 쿼리의 ?부분에 주입될 값
		this.jdbcTemplate.update(createReviewQuery, createReviewParams);

		String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 Row의 id값을 가져온다.
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery,
				Integer.class);
	}

	public int saveReviewForSingleHashTag(PostReviewReq req) {
		String createReviewQuery = "insert into Review ("
				+ "title, category, hashtag1,regionId, contents, userId"
				+ ") VALUES (?,?,?,?,?,?)";
		Object[] createReviewParams = new Object[] {
				req.getTitle(),
				req.getCategory(),
				req.getHashtags().get(0),
				req.getRegionId(),
				req.getContents(),
				req.getUserId()
		}; // 동적 쿼리의 ?부분에 주입될 값
		this.jdbcTemplate.update(createReviewQuery, createReviewParams);

		String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 Row의 id값을 가져온다.
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery,
				Integer.class);
	}

}
