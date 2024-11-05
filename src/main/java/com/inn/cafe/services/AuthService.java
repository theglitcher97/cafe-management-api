package com.inn.cafe.services;

import com.inn.cafe.VOS.RecoverPasswordVO;
import com.inn.cafe.VOS.UserVO;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;

public interface AuthService {
  void signup(UserVO userVO) throws BadRequestException;

  String login(UserVO userVO) throws BadRequestException;

  void recoverPassword(RecoverPasswordVO recoverPassword) throws MessagingException;
}
