package com.inn.cafe.dao;

import com.inn.cafe.entities.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface ProductDAO extends JpaRepository<Product, Integer> {
  List<ProductWrapper> getAllProducts();

  ProductWrapper findProductById(@Param("id") Integer id);

  @Modifying
  @Transactional
  void updateProductStatus(@Param("id") Integer id,@Param("status")  String status);

  List<ProductWrapper> findProductsByCategoryId(@Param("id") Integer id);
}
