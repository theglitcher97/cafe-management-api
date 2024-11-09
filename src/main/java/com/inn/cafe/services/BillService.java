package com.inn.cafe.services;

import com.inn.cafe.VOS.GenerateReportVO;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import org.apache.coyote.BadRequestException;

public interface BillService {

  String generateReport(GenerateReportVO generateReportVO) throws BadRequestException;
}
