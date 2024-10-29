package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.dao.UserDAO;
import com.inn.cafe.entities.User;
import com.inn.cafe.jwt.CustomerUserDetailService;
import com.inn.cafe.jwt.JwtUtil;
import com.inn.cafe.services.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private CustomerUserDetailService userDetailService;
  @Autowired private UserDAO userDAO;


  @Override
  @Transactional
  public void signup(UserVO userVO) throws BadRequestException {
    if (!this.validateUserData(userVO)) throw new BadRequestException("");

    User user = this.userDAO.findByEmail(userVO.getEmail());
    if (user != null) throw new EntityExistsException();

    user = this.newUserFromUserVo(userVO);
    this.userDAO.save(user);
  }

  @Override
  public String login(UserVO userVO) throws BadRequestException {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userVO.getEmail(), userVO.getPassword());
    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

    if (!authentication.isAuthenticated()) throw new BadRequestException("");

    if (this.userDetailService.getUserDetails().getStatus().equalsIgnoreCase("false"))
      throw new BadRequestException("Waiting for admin approval");

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return this.jwtUtil.generateToken(
        this.userDetailService.getUserDetails().getEmail(),
        this.userDetailService.getUserDetails().getRole());
  }

  private boolean validateUserData(UserVO userVO) {
    if (userVO.getName() == null || userVO.getName().isBlank()) return false;
    if (userVO.getEmail() == null || userVO.getEmail().isBlank()) return false;
    if (userVO.getPassword() == null || userVO.getPassword().isBlank()) return false;
    if (userVO.getContactNumber() == null || userVO.getContactNumber().isBlank()) return false;

    return true;
  }

  private User newUserFromUserVo(UserVO userVO) {
    User user = new User();
    user.setName(userVO.getName());
    user.setEmail(userVO.getEmail());
    user.setPassword(userVO.getPassword());
    user.setContactNumber(userVO.getContactNumber());
    user.setStatus("false");
    user.setRole("user");
    return user;
  }
}
