package com.inn.cafe.services.service_impl;

import com.google.gson.Gson;
import com.inn.cafe.VOS.BillVO;
import com.inn.cafe.VOS.GenerateReportVO;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.dao.BillDAO;
import com.inn.cafe.dtos.ProductsDetailsDTO;
import com.inn.cafe.entities.Bill;
import com.inn.cafe.jwt.JwtFilter;
import com.inn.cafe.services.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.apache.coyote.BadRequestException;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
  @Autowired private JwtFilter jwtFilter;
  @Autowired private BillDAO billDAO;

  public static String getUUID() {
    Date date = new Date();
    long time = date.getTime();
    return "BILL-" + time;
  }

  @Override
  public String generateReport(GenerateReportVO generateReportVO) throws BadRequestException {
    if (!this.isValidReport(generateReportVO)) throw new BadRequestException("");

    String filename;
    if (!generateReportVO.getIsGenerate()) filename = generateReportVO.getUuid();
    else {
      filename = getUUID();
      generateReportVO.setUuid(filename);
      this.insertBill(generateReportVO);
    }

    try {
      this.generateReportPDF(generateReportVO, filename);
      return filename;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Bill> findAllBills() {
    List<Bill> bills;

    if (this.jwtFilter.isAdmin()) bills = this.billDAO.getAllBills();
    else bills = this.billDAO.getBillsByUsername(this.jwtFilter.getCurrentUserName());

    return bills;
  }

  @Override
  public byte[] getPdf(GenerateReportVO reportVO) throws BadRequestException {
    byte[] pdf = new byte[0];

    if (reportVO.getUuid() == null || reportVO.getUuid().isBlank())
      return pdf;

    String filePath = CafeConstants.REPORTS_DIR+"/"+reportVO.getUuid()+".pdf";

    if (!CafeUtils.fileExists(filePath)) {
      reportVO.setIsGenerate(false);
      this.generateReport(reportVO);
    }

    pdf = this.getByteArray(filePath);

    return pdf;
  }

  private byte[] getByteArray(String filePath) {
    File initialFile = new File(filePath);
    try {
      InputStream targetStream = new FileInputStream(initialFile);
      byte[] byteArray = IOUtils.toByteArray(targetStream);
      targetStream.close();
      return byteArray;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private void generateReportPDF(GenerateReportVO generateReportVO, String filename)
      throws FileNotFoundException, DocumentException {
    String data =
        String.format(
            "Name %s\nContact Number: %s\nEmail: %s\nPayment Method: %s",
            generateReportVO.getName(),
            generateReportVO.getContactNumber(),
            generateReportVO.getEmail(),
            generateReportVO.getPaymentMethod());

    Document document = new Document();
    PdfWriter.getInstance(
        document, new FileOutputStream(CafeConstants.REPORTS_DIR + "/" + filename + ".pdf"));

    document.open();
    this.setRectangleInPdf(document);

    Paragraph chunk = new Paragraph("Cafe Management System", this.getFont("header"));
    chunk.setAlignment(Element.ALIGN_CENTER);
    document.add(chunk);

    Paragraph paragraph = new Paragraph(data + "\n\n", getFont("data"));
    document.add(paragraph);

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    this.addTableHeader(table);

    for (ProductsDetailsDTO productDetails : generateReportVO.getProductDetails()) {
      if (productDetails != null) this.addRow(table, productDetails);
    }
    document.add(table);

    Paragraph footer =
        new Paragraph(
            "Total: "
                + generateReportVO.getTotalAmount()
                + "\nThanks you for visiting. Please visit again!!",
            this.getFont("data"));

    document.add(footer);
    document.close();
  }

  private void addRow(PdfPTable table, ProductsDetailsDTO productDetails) {
    table.addCell(productDetails.name());
    table.addCell(productDetails.category());
    table.addCell(productDetails.quantity().toString());
    table.addCell(productDetails.price().toString());
    table.addCell((productDetails.price() * productDetails.quantity()) + "");
  }

  private void addTableHeader(PdfPTable table) {
    Stream.of("Name", "Category", "Quantity", "Price", "Subtotal")
        .forEach(
            column -> {
              PdfPCell header = new PdfPCell();
              header.setBorderWidth(2);
              header.setPhrase(new Phrase(column));
              header.setBackgroundColor(BaseColor.LIGHT_GRAY);
              header.setHorizontalAlignment(Element.ALIGN_CENTER);
              header.setVerticalAlignment(Element.ALIGN_CENTER);
              table.addCell(header);
            });
  }

  private Font getFont(String header) {
    switch (header) {
      case "header":
        Font headerFont =
            FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
        headerFont.setSize(Font.BOLD);
        return headerFont;
      case "data":
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
        dataFont.setStyle(Font.BOLD);
        return dataFont;
      default:
        return new Font();
    }
  }

  private void setRectangleInPdf(Document document) throws DocumentException {
    Rectangle rect = new Rectangle(577, 825, 18, 15);
    rect.enableBorderSide(1);
    rect.enableBorderSide(2);
    rect.enableBorderSide(4);
    rect.enableBorderSide(8);

    rect.setBackgroundColor(BaseColor.WHITE);
    rect.setBorderWidth(1);
    document.add(rect);
  }

  private void insertBill(GenerateReportVO generateReportVO) {
    Bill bill = new Bill();
    bill.setName(generateReportVO.getName());
    bill.setUuid(generateReportVO.getUuid());
    bill.setEmail(generateReportVO.getEmail());
    bill.setTotal(generateReportVO.getTotalAmount());
    bill.setCreatedBy(this.jwtFilter.getCurrentUserName());
    bill.setContactNumber(generateReportVO.getContactNumber());
    bill.setPaymentMethod(generateReportVO.getPaymentMethod());
    bill.setProductDetails(new Gson().toJson(generateReportVO.getProductDetails()));
    this.billDAO.save(bill);
  }

  private boolean isValidReport(GenerateReportVO generateReportVO) {
    if (generateReportVO.getEmail() == null
        || generateReportVO.getEmail().isBlank()
        || generateReportVO.getName() == null
        || generateReportVO.getName().isBlank()
        || generateReportVO.getPaymentMethod() == null
        || generateReportVO.getPaymentMethod().isBlank()
        || generateReportVO.getProductDetails() == null
        || generateReportVO.getContactNumber() == null
        || generateReportVO.getContactNumber().isBlank()
        || generateReportVO.getTotalAmount() == null) return false;

    return true;
  }
}
