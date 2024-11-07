package com.inn.cafe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "Product.getAllProducts", query = "Select new com.inn.cafe.wrapper.ProductWrapper(id, name, price, description, p.category.id, p.category.name) From Product p")

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
@Data // generate getters, setters and default constructor
public class Product implements Serializable {

  private static final long serialVersionUID = 123456L;


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_fk", nullable = false)
  private Category category;

  private String description;

  private Integer price;

  private String status;
}
