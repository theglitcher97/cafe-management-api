package com.inn.cafe.wrapper;

import com.inn.cafe.entities.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {

  private Integer id;

  private String name;

  private String description;

  private Integer price;

  private String categoryName;

  private Integer categoryId;

  public ProductWrapper(Integer id, String name, Integer price, String description, Integer categoryId, String categoryName){
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }

  public ProductWrapper(Integer id, String name, Integer price, String description){
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
  }

  public ProductWrapper(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}
