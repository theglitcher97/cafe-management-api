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

  @Override
  public ResponseEntity<String> deleteProduct(int id) {
    try {
      this.productService.removeProduct(id);
      return CafeUtils.getResponseEntity("OK", HttpStatus.OK);
    } catch (EntityNotFoundException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public ResponseEntity<ProductWrapper> getProductById(Integer id) {
    try {
      ProductWrapper product = this.productService.getProductById(id);
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (AuthorizationServiceException e) {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public ResponseEntity<String> updateProductStatus(ProductVO productVO) {
    try {
      this.productService.updateProductStatus(productVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.OK);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (EntityNotFoundException e) {
      return CafeUtils.getResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public ResponseEntity<List<ProductWrapper>> getProductsByCategory(Integer id) {
    try {
      List<ProductWrapper> products = this.productService.findProductsByCategoryId(id);
      return new ResponseEntity<>(products, HttpStatus.OK);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }
}
