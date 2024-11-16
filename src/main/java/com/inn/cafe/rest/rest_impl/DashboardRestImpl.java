package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.DashboardDetailsVO;
import com.inn.cafe.rest.DashboardRest;
import com.inn.cafe.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardRestImpl implements DashboardRest {
  @Autowired
  private DashboardService dashboardService;

  @Override
  public ResponseEntity<DashboardDetailsVO> getCount() {
    return new ResponseEntity<>(this.dashboardService.getCount(), HttpStatus.OK);
  }
}
