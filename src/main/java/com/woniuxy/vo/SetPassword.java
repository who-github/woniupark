package com.woniuxy.vo;

import lombok.Data;

@Data
public class SetPassword {
    private Integer userId;
    private String password;
    private String newpassword;
}
