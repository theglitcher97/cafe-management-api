package com.inn.cafe.dao;

import com.inn.cafe.entities.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
  List<ProductWrapper> getAllProducts();
}
