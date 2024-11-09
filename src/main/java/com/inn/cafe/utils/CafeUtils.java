package com.inn.cafe.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inn.cafe.dtos.ProductsDetailsDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
  private CafeUtils(){}

  public static ResponseEntity<String> getResponseEntity(String response, HttpStatus httpStatus) {
    return new ResponseEntity<>("{\"message\": \""+response+"\"}", httpStatus);
  }

  public static JSONArray getJsonArrayFromString(String productDetails) throws JSONException {
    return new JSONArray(productDetails);
  }

  public static ProductsDetailsDTO getProductDetails(String strObj) {
    if ( Strings.isNullOrEmpty(strObj))
      return null;
    return new Gson().fromJson(strObj, new TypeToken<ProductsDetailsDTO>(){}.getType());
  }
}
