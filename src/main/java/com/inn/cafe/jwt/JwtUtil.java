package com.inn.cafe.jwt;

import com.inn.cafe.constants.CafeConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {
  private final String MEGA_SECRET_KEY = "adspoifnipadsgunasdpufnpaisdnfasidfapuisbfa";

  public String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + CafeConstants.ONE_HOUR))
        .signWith(SignatureAlgorithm.HS256, this.MEGA_SECRET_KEY)
        .compact();
  }

  public String generateToken(String username, String role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    return this.createToken(claims, username);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return this.extractExpirationDate(token).before(new Date());
  }

  // in our case the 'username' is the 'email'
  public String extractUsername(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  // return the expiration date associated with the token
  public Date extractExpirationDate(String token) {
    return this.extractClaims(token, Claims::getExpiration);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }


  public Claims extractAllClaims(String token) {
    return (Claims) Jwts.parser().setSigningKey(this.MEGA_SECRET_KEY).build().parse(token).getPayload();
  }



}
