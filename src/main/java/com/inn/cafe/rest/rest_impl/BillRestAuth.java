package com.inn.cafe.rest.rest_impl;

import com.inn.cafe.VOS.BillVO;
import com.inn.cafe.VOS.GenerateReportVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.Bill;
import com.inn.cafe.rest.BillRest;
import com.inn.cafe.services.BillService;
import com.inn.cafe.utils.CafeUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestAuth implements BillRest {
  @Autowired
  private BillService billService;

  @Override
  public ResponseEntity<String> generateReport(GenerateReportVO generateReportVO) {
    try {
      String fileName = this.billService.generateReport(generateReportVO);
      return CafeUtils.getResponseEntity(fileName, HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<Bill>> getBills() {
    try {
      return new ResponseEntity<>(this.billService.findAllBills(), HttpStatus.OK);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  public ResponseEntity<byte[]> getPdf(GenerateReportVO reportVo) {
    try {
      return new ResponseEntity<>(this.billService.getPdf(reportVo), HttpStatus.OK);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
