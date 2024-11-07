package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.ProductVO;
import com.inn.cafe.dao.CategoryDAO;
import com.inn.cafe.dao.ProductDAO;
import com.inn.cafe.entities.Category;
import com.inn.cafe.entities.Product;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.services.ProductService;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired private ProductDAO productDAO;
  @Autowired private CategoryDAO categoryDAO;

  @Autowired private JwtFilter jwtFilter;

  @Override
  public void create(ProductVO productVO) throws BadRequestException {
    if (!this.isValidProduct(productVO, true)) throw new BadRequestException();
    if (!this.jwtFilter.isAdmin()) throw new AuthorizationServiceException("");

    Category category = this.categoryDAO.findById(productVO.getCategory()).orElse(null);
    if (category == null)
      throw new EntityNotFoundException("Category not found");

    Product product = this.createProductFromVO(productVO);
    product.setCategory(category);

    this.productDAO.save(product);
  }

  @Override
  public List<ProductWrapper> getAll() {
    return this.productDAO.getAllProducts();
  }

  @Override
  public void update(ProductVO productVO) throws BadRequestException {
    if (!this.isValidProduct(productVO, false)) throw new BadRequestException();
    if (!this.jwtFilter.isAdmin()) throw new AuthorizationServiceException("");

    Product product = this.productDAO.findById(productVO.getId()).orElse(null);
    if (product == null)
      throw new EntityNotFoundException("Product not found");

    product.setName(productVO.getName());
    product.setPrice(productVO.getPrice());
    product.setDescription(productVO.getDescription());
    product.setStatus(productVO.getStatus());

    this.productDAO.save(product);
  }

  private Product createProductFromVO(ProductVO productVO) {
    Product product = new Product();
    product.setName(productVO.getName());
    product.setPrice(productVO.getPrice());
    product.setDescription(productVO.getDescription());
    product.setStatus("true");
    return product;
  }

  private boolean isValidProduct(ProductVO productVO, boolean isNew) {
    if (!isNew && productVO.getId() == null) return false;

    if ((isNew && productVO.getCategory() == null)
        || productVO.getName() == null
        || productVO.getDescription() == null
        || productVO.getPrice() == null) return false;

    if (productVO.getName().isBlank()
        || productVO.getDescription().isBlank()
        || productVO.getPrice() < 0) return false;

    return true;
  }
}
