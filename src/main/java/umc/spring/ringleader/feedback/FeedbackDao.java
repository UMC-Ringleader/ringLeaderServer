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
}
