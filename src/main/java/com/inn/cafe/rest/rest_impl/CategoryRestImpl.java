package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.CategoryVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.Category;
import com.inn.cafe.rest.CategoryRest;
import com.inn.cafe.services.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestImpl implements CategoryRest {
  @Autowired private CategoryService categoryService;

  @Override
  public ResponseEntity<String> saveNewCategory(CategoryVO categoryVO) {
    try {
      this.categoryService.storeNewCategory(categoryVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.CREATED);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
    List<Category> categories = this.categoryService.getAllCategories(filterValue);
    return ResponseEntity.ok(categories);
  }

  @Override
  public ResponseEntity<String> updateCategory(CategoryVO categoryVO) {
    try {
      this.categoryService.updateCategory(categoryVO);
      return CafeUtils.getResponseEntity("OK", HttpStatus.OK);
    } catch (BadRequestException e) {
      return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    } catch (AuthorizationServiceException e) {
      return CafeUtils.getResponseEntity(CafeConstants.NOT_AUTHORIZED, HttpStatus.UNAUTHORIZED);
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(CafeConstants.ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
  }
}
