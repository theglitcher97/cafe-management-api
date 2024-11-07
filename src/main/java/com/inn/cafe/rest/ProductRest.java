package com.inn.cafe.rest;

import com.inn.cafe.VOS.CategoryVO;
import com.inn.cafe.VOS.ProductVO;
import com.inn.cafe.entities.Category;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.websocket.server.PathParam;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/products")
public interface ProductRest {
  @PostMapping()
  ResponseEntity<String> saveNewProduct(@RequestBody() ProductVO productVO);

  @GetMapping()
  ResponseEntity<List<ProductWrapper>> getProducts();

  @PutMapping()
  ResponseEntity<String> updateProduct(@RequestBody() ProductVO productVO);

  @DeleteMapping("/{id}")
  ResponseEntity<String> deleteProduct(@PathVariable("id") int id);

  @GetMapping(path = "/{id}")
  ResponseEntity<ProductWrapper> getProductById(@PathVariable("id") Integer id);

  @PutMapping(path = "/status")
  ResponseEntity<String> updateProductStatus(@RequestBody() ProductVO productVO);

  @GetMapping(path = "/category/{id}")
  ResponseEntity<List<ProductWrapper>> getProductsByCategory(@PathVariable("id") Integer id);
}
