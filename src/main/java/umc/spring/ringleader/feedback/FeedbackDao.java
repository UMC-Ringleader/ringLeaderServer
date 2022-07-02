package umc.spring.ringleader.feedback;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	public int getReviewFeedBackByComment(int reviewId, String comment) {
		String findLikes = "select count(*) from reviewFeedback where reviewId = ? AND comment = ?";
		return this.jdbcTemplate.queryForObject(findLikes, Integer.class, reviewId, comment);
	}

	//존재 여부 확인
	//success 로 파싱되어 반환되며 존재하면 1, 존재하지 않으면 0을 반환한다.
	public int existingVerification(int userId, int reviewId) {
		String query = "select EXISTS(select * from reviewFeedback where userId = ? and reviewId = ? limit 1) as success;\n";
		Object[] params = {userId, reviewId};

		return jdbcTemplate.queryForObject(query, int.class,params);
	}

	//피드백 생성
	public int createFeedback(int userId, int reviewId, String comment){
		String query = "INSERT INTO reviewFeedback(userId,reviewId,comment) VALUES(?,?,?)";
		Object[] params = {userId, reviewId, comment};

		return jdbcTemplate.update(query, params);
	}
	//피드백 삭제
	public int deleteFeedback(int userId, int reviewId) {
		return jdbcTemplate.update("delete from reviewFeedback where userId= ? and reviewId =? ", userId, reviewId);
	}

	//comment 받아요기
	public String getComment(int userId,int reviewId){
		String query = "select comment from reviewFeedback where userId =? and reviewId =?";
		Object[] params = {userId,reviewId};
		return jdbcTemplate.queryForObject(query, String.class, params);
	}


	public int getRegionIdByReviewId(int reviewId) {
		String getReviewImgs = "select regionId from Review where reviewId = ?";
		return this.jdbcTemplate.queryForObject(getReviewImgs, Integer.class, reviewId);
	}

}
