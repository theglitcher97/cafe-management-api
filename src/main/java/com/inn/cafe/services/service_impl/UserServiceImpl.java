package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.dao.UserDAO;
import com.inn.cafe.entities.User;
import com.inn.cafe.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
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

  private boolean validateUserData(UserVO userVO) {
    if(userVO.getName() == null || userVO.getName().isBlank()) return false;
    if(userVO.getEmail() == null || userVO.getEmail().isBlank()) return false;
    if(userVO.getPassword() == null || userVO.getPassword().isBlank()) return false;
    if(userVO.getContactNumber() == null || userVO.getContactNumber().isBlank()) return false;

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
