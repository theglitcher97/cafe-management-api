package com.inn.cafe.rest;

import com.inn.cafe.VOS.UserVO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserRest {

  @PostMapping("/signup")
  ResponseEntity<String> signup(@RequestBody(required = true) UserVO userVO);

  @PostMapping("/login")
  ResponseEntity<String> login(@RequestBody(required = true) UserVO userVO);
}
