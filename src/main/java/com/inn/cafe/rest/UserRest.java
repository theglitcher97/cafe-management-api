package com.inn.cafe.rest;

import com.inn.cafe.VOS.UserVO;
import com.inn.cafe.wrapper.UserWrapper;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserRest {

  @GetMapping
  ResponseEntity<List<UserWrapper>> getUsers();

  @PutMapping("/{id}")
  ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody UserVO userVO);
}
