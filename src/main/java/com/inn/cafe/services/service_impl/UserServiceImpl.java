package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.UserDAO;
import com.inn.cafe.entities.User;
import com.inn.cafe.jwt.CustomerUserDetailService;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.jwt.JwtUtil;
import com.inn.cafe.services.UserService;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  @Autowired private UserDAO userDAO;
  @Autowired private JwtFilter jwtFilter;


  @Override
  public List<UserWrapper> getUsers() {
    if (!this.jwtFilter.isAdmin())
      throw new AuthorizationServiceException(CafeConstants.NOT_AUTHORIZED);

    return this.userDAO.findAllUsers();
  }

  @Override
  @Transactional
  public void update(int id, UserVO userVO) throws BadRequestException {
    if (!this.jwtFilter.isAdmin())
      throw new AuthorizationServiceException(CafeConstants.NOT_AUTHORIZED);

    if (userVO.getStatus() == null || userVO.getStatus().isBlank())
      throw new BadRequestException(CafeConstants.INVALID_DATA);

    User user = this.userDAO.findById(id).orElse(null);
    if (user == null)
      throw new EntityNotFoundException(CafeConstants.ENTITY_NOT_FOUND);

    user.setStatus(userVO.getStatus());
    this.userDAO.save(user);

//    List<UserWrapper> admins = this.userDAO.findAllAdmins();
  }



}
