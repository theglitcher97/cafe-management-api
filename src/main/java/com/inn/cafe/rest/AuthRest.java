package com.inn.cafe.rest;

import com.inn.cafe.VOS.UserVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthRest {
  @PostMapping(path = "/signup")
  ResponseEntity<String> signup(@RequestBody(required = true) UserVO userVO);

  @PostMapping(path ="/login")
  ResponseEntity<String> login(@RequestBody(required = true) UserVO userVO);
}
