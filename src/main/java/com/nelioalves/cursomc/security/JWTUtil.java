package com.nelioalves.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	//secret e expiration encontram-se no application.properties
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		// gerar token
		// SignatureAlgorithm tem uma lista de algorítimos que podemos escolher, HS512 é um algorítimo bem poderoso.

		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // hora do sistema + tempo de expiration que está em 60000 milesegundos
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		
		/*
		final Date createdDate = new Date(); 
		createdDate.getTime();
		final Date expirationDate = createdDate;
		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		*/
	}
	
	// testar se o token é válido.
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e){
			return null;
		}
	}
	
}
