package com.inn.cafe.services;


import com.inn.cafe.VOS.ProductVO;
import com.inn.cafe.wrapper.ProductWrapper;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  void create(ProductVO productVO) throws BadRequestException;

  List<ProductWrapper> getAll();

  void update(ProductVO productVO) throws BadRequestException;

  void removeProduct(int id);

  ProductWrapper getProductById(Integer id);

  void updateProductStatus(ProductVO productVO) throws BadRequestException;

  List<ProductWrapper> findProductsByCategoryId(Integer id);
}
