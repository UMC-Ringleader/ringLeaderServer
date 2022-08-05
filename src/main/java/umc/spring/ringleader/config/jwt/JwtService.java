package umc.spring.ringleader.config.jwt;

import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {

	private final DateUtil dateUtil;
	@Value("${jwt.secret}")
	private String JWT_SECRET_KEY;

	public String createMemberAccessToken(int id, String email) {
		LocalDateTime now = LocalDateTime.now();

		return Jwts.builder()
			.setHeaderParam("type", "jwt")
			.claim("memberId", id)
			.claim("memberEmail", email)
			.setIssuedAt(dateUtil.toDate(now))
			.setExpiration(dateUtil.toDate(now.plusDays(7)))
			.signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
			.compact();
	}
}

