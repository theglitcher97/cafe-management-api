package com.inn.cafe.utils;

import java.io.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
  private CafeUtils(){}

  public static ResponseEntity<String> getResponseEntity(String response, HttpStatus httpStatus) {
    return new ResponseEntity<>("{\"message\": \""+response+"\"}", httpStatus);
  }

  public static boolean fileExists(String filePath) {
    try {
      File file = new File(filePath);
      return file.exists();
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
