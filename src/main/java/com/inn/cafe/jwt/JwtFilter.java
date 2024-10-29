package com.inn.cafe.jwt;

import com.inn.cafe.constants.CafeConstants;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired private JwtUtil jwtUtil;
  @Autowired private CustomerUserDetailService userDetailService;

  private Claims claims;
  private String username;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // get token from request
    String token = this.getTokenFromRequest(request);
    // if not token continue
    if (Objects.isNull(token)){
      filterChain.doFilter(request, response);
      return;
    }

    this.username = this.jwtUtil.extractUsername(token);
    this.claims = this.jwtUtil.extractAllClaims(token);

    // TODO
    if (Objects.isNull(username)) {
      filterChain.doFilter(request, response);
      return;
    }

    UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

    // TODO
    if (!this.jwtUtil.validateToken(token, userDetails)){
      filterChain.doFilter(request, response);
      return;
    }


    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }

  public boolean isAdmin() {
    return CafeConstants.ADMIN.equalsIgnoreCase((String) claims.get("role"));
  }

  public boolean isUser() {
    return CafeConstants.USER.equalsIgnoreCase((String) claims.get("role"));
  }

  private String  getCurrentUserName(){
    return this.username;
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    // get authorization header
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null)
      return null;
    if (authHeader.isBlank())
      return null;
    if (!authHeader.startsWith("Bearer"))
      return null;


    // get token from header
    // header = "Bearer {token}"
    return authHeader.split(" ")[1];
  }
}
