package com.inn.cafe.rest;

import com.inn.cafe.VOS.CategoryVO;
import com.inn.cafe.entities.Category;
import jakarta.websocket.server.PathParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/categories")
public interface CategoryRest {

  @PostMapping(path = "")
  ResponseEntity<String> saveNewCategory(@RequestBody() CategoryVO categoryVO);

  @GetMapping()
  ResponseEntity<List<Category>> getAllCategories(@PathParam("filterValue") String filterValue);

  @PutMapping()
  ResponseEntity<String> updateCategory(@RequestBody() CategoryVO categoryVO);
}
