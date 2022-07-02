package umc.spring.ringleader.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.ringleader.login.DTO.PostLoginReq;
import umc.spring.ringleader.login.DTO.PostSignupReq;

import javax.sql.DataSource;

@Repository
public class LoginDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    /**
     * 이메일로 비밀번호 조회
     *
     * @param email
     * @return
     */
    public String getPwdByEmail(String email) {
        String getPwdQuery = "select password from User where email = ?";
        return this.jdbcTemplate.queryForObject(getPwdQuery, String.class, email);
    }

    public Integer getUserIdByEmail(String email) {
        String getUserIdQuery = "select userId from User where email = ?";
        return this.jdbcTemplate.queryForObject(getUserIdQuery, Integer.class, email);
    }

    /**
     * 비밀번호 일치하는지 조회
     *
     * @param password
     * @return
     */
    public Integer correspondPwd(String password) {
        String getUserIdQuery = "select userId from User where password = ?";

        // 비밀번호 일치하는 경우
        if (getUserIdQuery != null) {
            return this.jdbcTemplate.queryForObject(getUserIdQuery, int.class, password);
        }
        // 비밀번호 일치하지 않는 경우
        else {
            return -1;
        }
    }

    /**
     * 이메일 중복 검사
     * @param email
     * @return
*/
    public boolean isCorrespondEmail(String email) {
        // 있으면 1, 없으면 0
        String getEmailOverlapQuery = "select exists(select userId from User where email = ?)";
        Integer flag = this.jdbcTemplate.queryForObject(getEmailOverlapQuery, Integer.class, email);
        return flag == 1;
    }

    public int saveUser(PostSignupReq req) {
        String saveUserQuery = "insert into User ("
                + "email, password"
                + ") VALUES (?,?)"; // 실행될 동적 쿼리문
        Object[] saveUserParams = new Object[]{
                req.getEmail(),
                req.getPwd(),
        }; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(saveUserQuery, saveUserParams);

        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된 User id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class); //UserId 반환
    }
}