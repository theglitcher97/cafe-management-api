package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.RecoverPasswordVO;
import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.rest.AuthRest;
import com.inn.cafe.services.AuthService;
import com.inn.cafe.utils.CafeUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuhtRestimpl implements AuthRest {
  @Autowired
  private AuthService authService;

  @Override
  public ResponseEntity<String> signup(UserVO userVO) {
    try {
      this.authService.signup(userVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (EntityExistsException e) {
      return CafeUtils.getResponseEntity(CafeConstants.EMAIL_EXISTS, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return CafeUtils.getResponseEntity(
        CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<String> login(UserVO userVO) {
    try {
      String token = this.authService.login(userVO);
      return CafeUtils.getResponseEntity(token, HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return CafeUtils.getResponseEntity(
        CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<String> forgotPassword(RecoverPasswordVO recoverPassword) {
    try {
      this.authService.recoverPassword(recoverPassword);
      return ResponseEntity.ok("OK");
    } catch (Exception e) {
      return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
