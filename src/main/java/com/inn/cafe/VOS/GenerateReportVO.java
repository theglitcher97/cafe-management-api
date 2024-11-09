package com.inn.cafe.VOS;

import com.inn.cafe.dtos.ProductsDetailsDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateReportVO {
  private String name;
  private String uuid;
  private String contactNumber;
  private String email;
  private String paymentMethod;
  private List<ProductsDetailsDTO> productDetails;
  private Integer totalAmount;
  private Boolean isGenerate;
}
