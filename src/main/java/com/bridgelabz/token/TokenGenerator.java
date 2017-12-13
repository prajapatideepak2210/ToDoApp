package com.bridgelabz.token;

import java.util.Calendar;
import java.util.Date;

import com.bridgelabz.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {
	private static String secretekey = "deepak";

	public static String generateToken(int userId, User user) {
		long nowMillis = System.currentTimeMillis();
		Date dateAndTime = new Date(nowMillis);
		String issuerTime = dateAndTime.toString();
		Calendar calender=Calendar.getInstance();
		calender.add(Calendar.MINUTE, 60);
		Date expireTime = calender.getTime();
		String token = Jwts.builder().setId(Integer.toString(userId)).setIssuedAt(dateAndTime).setIssuer(issuerTime).setExpiration(expireTime)
				.signWith(SignatureAlgorithm.HS256, secretekey).compact().toString();
		return token;
	}

	public static int verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretekey).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getId());
	}
	
	
}
