package com.inn.cafe.jwt;

import com.inn.cafe.dao.UserDAO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerUserDetailService implements UserDetailsService {
  @Autowired
  private UserDAO userDAO;

  // our entity
  @Getter
  private com.inn.cafe.entities.User userDetails;

  @Override
  public UserDetails loadUserByUsername(String username) {
    log.info("loading user by username "+username);
    userDetails = this.userDAO.findByEmail(username);
    if ( Objects.isNull(userDetails))
      throw new UsernameNotFoundException("User not found!");
    return new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
  }

  public com.inn.cafe.entities.User getUser(){
    userDetails.setPassword(null);
    return this.userDetails;
  }

}
