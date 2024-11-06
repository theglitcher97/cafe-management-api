package com.inn.cafe.dao;

import com.inn.cafe.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
  List<Category> getAllCategories();
}
