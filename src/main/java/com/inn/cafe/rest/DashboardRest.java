package com.inn.cafe.rest;

import com.inn.cafe.VOS.DashboardDetailsVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/dashboard")
public interface DashboardRest {

  @GetMapping(path = "/details")
  ResponseEntity<DashboardDetailsVO> getCount();
}
