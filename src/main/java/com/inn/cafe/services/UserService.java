package com.inn.cafe.services;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.wrapper.UserWrapper;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface UserService {



  List<UserWrapper> getUsers();

  void update(int id, UserVO userVO) throws BadRequestException;
}
