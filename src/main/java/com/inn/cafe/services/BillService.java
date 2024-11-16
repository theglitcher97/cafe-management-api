package com.inn.cafe.services;

import com.inn.cafe.VOS.BillVO;
import com.inn.cafe.VOS.GenerateReportVO;
import com.inn.cafe.entities.Bill;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface BillService {

  String generateReport(GenerateReportVO generateReportVO) throws BadRequestException;

  List<Bill> findAllBills();

  byte[] getPdf(GenerateReportVO reportVO) throws BadRequestException;

  void removeBill(Integer id);
}
