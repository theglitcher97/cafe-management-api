package com.inn.cafe.VOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordVO {
  private String oldPassword;
  private String newPassword;
}
