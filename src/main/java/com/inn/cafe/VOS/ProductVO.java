package com.inn.cafe.VOS;

import com.inn.cafe.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {

  private Integer id;
  private String name;
  private Integer category;
  private String description;
  private Integer price;
  private String status;
}
