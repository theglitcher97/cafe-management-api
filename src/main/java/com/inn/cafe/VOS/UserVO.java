package com.inn.cafe.VOS;


import lombok.Data;

@Data
public class UserVO {
    private String email;
    private String password;
    private String status;
    private String role;
    private String name;
    private String contactNumber;
}
