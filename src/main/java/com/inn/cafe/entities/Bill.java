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

// Named Queries
@NamedQuery(name = "Bill.getAllBills", query = "Select b from Bill b order by b.id desc")
@NamedQuery(name = "Bill.getBillsByUsername", query = "Select b from Bill b where b.createdBy = :createdBy order by b.id desc")
@NamedQuery(name = "Bill.removeBill", query = "Delete from Bill b where b.uuid = :uuid")

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "bill")
@Data // generate getters, setters and default constructor
public class Bill implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String uuid;
  private String name;
  private String email;

  @Column(name = "contact_number")
  private String contactNumber;

  @Column(name = "payment_method")
  private String paymentMethod;

  private Integer total;

  @Column(name = "product_details", columnDefinition = "json")
  private String productDetails;

  @Column(name = "created_by")
  private String createdBy;


}
