package com.inn.cafe.services;

import com.inn.cafe.VOS.CategoryVO;
import com.inn.cafe.entities.Category;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface CategoryService {

  void storeNewCategory(CategoryVO categoryVO) throws BadRequestException;

  List<Category> getAllCategories(String filterValue);

  void updateCategory(CategoryVO categoryVO) throws BadRequestException;
}
