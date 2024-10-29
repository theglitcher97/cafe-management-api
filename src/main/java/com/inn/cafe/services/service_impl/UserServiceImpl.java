package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.UserDAO;
import com.inn.cafe.entities.User;
import com.inn.cafe.jwt.CustomerUserDetailService;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.jwt.JwtUtil;
import com.inn.cafe.services.UserService;
import com.inn.cafe.utils.EmailUtils;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
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
  @Autowired private EmailUtils emailUtils;

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
    if (user == null) throw new EntityNotFoundException(CafeConstants.ENTITY_NOT_FOUND);

    user.setStatus(userVO.getStatus());
    this.userDAO.save(user);

    List<String> admins = this.userDAO.findAllAdmins();
    this.sendEmailToAdmins(userVO.getStatus(), user.getEmail(), admins);
  }

  private void sendEmailToAdmins(String status, String user, List<String> admins) {
    // remove current admin from of the list of admin to send email
    admins.remove(this.jwtFilter.getCurrentUserName());
    if (status == null)
      return;

    String accStatus = status.equalsIgnoreCase("true") ? "Approved" : "Disable";
    String subject = "Account "+accStatus;
    String body = "User:- "+user+"\n has been "+accStatus+" by Admin: "+jwtFilter.getCurrentUserName();

    this.emailUtils.sendSimpleMessage(this.jwtFilter.getCurrentUserName(), subject, body, admins);
  }
}
