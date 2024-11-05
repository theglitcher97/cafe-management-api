package com.inn.cafe.VOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordVO {
  private String email;
  private String name;
  private String contactNumber;
}
