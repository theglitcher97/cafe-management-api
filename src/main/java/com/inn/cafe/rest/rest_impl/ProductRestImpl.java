package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.ProductVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.rest.ProductRest;
import com.inn.cafe.services.ProductService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestImpl implements ProductRest {
  @Autowired
  private ProductService productService;

  @Override
  public ResponseEntity<String> saveNewProduct(ProductVO productVO) {
    try {
      this.productService.create(productVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (EntityNotFoundException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public ResponseEntity<List<ProductWrapper>> getProducts() {
    List<ProductWrapper> productWrappers = this.productService.getAll();
    return ResponseEntity.ok(this.productService.getAll());
  }

  @Override
  public ResponseEntity<String> updateProduct(ProductVO productVO) {
    try {
      this.productService.update(productVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.OK);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (EntityNotFoundException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }
}
