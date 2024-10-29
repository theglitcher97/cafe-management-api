package com.inn.cafe.services;

import com.inn.cafe.VOS.UserVO;
import org.apache.coyote.BadRequestException;

public interface UserService {

  void signup(UserVO userVO) throws BadRequestException;

  String login(UserVO userVO) throws BadRequestException;
}
