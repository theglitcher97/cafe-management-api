package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.ChangePasswordVO;
import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.rest.UserRest;
import com.inn.cafe.services.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.UserWrapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestImpl implements UserRest {
  @Autowired private UserService userService;

  @Override
  public ResponseEntity<List<UserWrapper>> getUsers() {
    try {
      List<UserWrapper> users = this.userService.getUsers();
      return ResponseEntity.ok(users);
    } catch (AuthorizationServiceException e) {
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<String> update(int id, UserVO userVO) {
    try {
      this.userService.update(id, userVO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (BadRequestException e) {
      return new ResponseEntity<>(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(CafeConstants.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<String> changePassword(ChangePasswordVO changePasswordVO) {
    try {
      this.userService.changePassword(changePasswordVO);
      return ResponseEntity.ok("OK");
    } catch (BadCredentialsException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (EntityNotFoundException e) {
      return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
