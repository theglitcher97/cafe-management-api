package com.inn.cafe.services.service_impl;

import com.inn.cafe.VOS.DashboardDetailsVO;
import com.inn.cafe.dao.BillDAO;
import com.inn.cafe.dao.CategoryDAO;
import com.inn.cafe.dao.ProductDAO;
import com.inn.cafe.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
  @Autowired private CategoryDAO categoryDAO;
  @Autowired private ProductDAO productDAO;
  @Autowired private BillDAO billDAO;

  @Override
  public DashboardDetailsVO getCount() {
    long category = this.categoryDAO.count();
    long product = this.productDAO.count();
    long bill = this.billDAO.count();
    return new DashboardDetailsVO(category, product, bill);
  }
}
