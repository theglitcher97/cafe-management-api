package com.inn.cafe.rest;

import com.inn.cafe.VOS.GenerateReportVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bill")
public interface BillRest {
  @PostMapping(path = "/generate-report")
  ResponseEntity<String> generateReport(@RequestBody() GenerateReportVO generateReportVO);
}
