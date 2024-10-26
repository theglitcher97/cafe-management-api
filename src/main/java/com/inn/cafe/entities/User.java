package com.inn.cafe.entities;

import jakarta.persistence.Column;
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

// links the class User with the method findByEmail (found in the DAO).
// to specify how to find a user by the email using this query.
// 'u.email' makes reference to the property 'email' in this class
// ':email' makes reference to the param 'email' found in the DAO
@NamedQuery(name = "User.findByEmail", query = "Select u from User u where u.email = :email")

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
@Data // generate getters, setters and default constructor
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String email;
  private String password;
  private String status;
  private String role;
  private String name;

  @Column(name = "contact_number")
  private String contactNumber;
}
