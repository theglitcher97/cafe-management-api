package com.inn.cafe.rest;

import com.inn.cafe.VOS.BillVO;
import com.inn.cafe.VOS.GenerateReportVO;
import com.inn.cafe.entities.Bill;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bills")
public interface BillRest {
  @PostMapping(path = "/generate-report")
  ResponseEntity<String> generateReport(@RequestBody() GenerateReportVO generateReportVO);

  @GetMapping()
  ResponseEntity<List<Bill>> getBills();

  @PostMapping(path = "/pdf")
  ResponseEntity<byte[]> getPdf(@RequestBody() GenerateReportVO reportVO);

  @DeleteMapping(path = "/{id}")
  ResponseEntity<String> deleteBill(@PathVariable("id") Integer id);
}
