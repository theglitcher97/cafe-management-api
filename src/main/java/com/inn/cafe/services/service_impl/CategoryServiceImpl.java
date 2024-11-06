package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.CategoryVO;
import com.inn.cafe.dao.CategoryDAO;
import com.inn.cafe.entities.Category;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired private CategoryDAO categoryDAO;
  @Autowired private JwtFilter jwtFilter;

  @Override
  @Transactional
  public void storeNewCategory(CategoryVO categoryVO) throws BadRequestException {
    if (!this.isValidCategory(categoryVO, true))
      throw new BadRequestException();

    if (!this.jwtFilter.isAdmin())
      throw new AuthorizationServiceException("");

    Category category = new Category(categoryVO.getName());

    this.categoryDAO.save(category);
  }

  @Override
  public List<Category> getAllCategories(String filterValue) {
    List<Category> categories;

    if (filterValue != null && filterValue.equalsIgnoreCase("true")) {
      categories = this.categoryDAO.getAllCategories();
    }
    else
      categories = this.categoryDAO.findAll();

    return categories;
  }

  @Override
  public void updateCategory(CategoryVO categoryVO) throws BadRequestException {
    if (!this.isValidCategory(categoryVO, false))
      throw new BadRequestException("");

    if (!this.jwtFilter.isAdmin())
      throw new AuthorizationServiceException("");

    Category category = this.categoryDAO.findById(categoryVO.getId()).orElse(null);
    if (category == null)
      throw new EntityNotFoundException("");

    category.setName(categoryVO.getName());
    this.categoryDAO.save(category);
  }

  private boolean isValidCategory(CategoryVO categoryVO, boolean isNew) {
    if (categoryVO.getName() == null || categoryVO.getName().isBlank())
      return false;

    return isNew || categoryVO.getId() != null;
  }
}
