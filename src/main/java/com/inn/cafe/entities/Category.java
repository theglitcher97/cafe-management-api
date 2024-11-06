package com.inn.cafe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/* Name Queries */
@NamedQuery(name = "Category.getAllCategories", query = "Select c from Category c")

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "category")
@Data // generate getters, setters and default constructor
public class Category implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  public Category(){}

  public Category(String name){
    this.name = name;
  }
}
