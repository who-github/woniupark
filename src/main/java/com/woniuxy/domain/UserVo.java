package com.woniuxy.domain;

import lombok.Data;

@Data
public class UserVo {
    private String username;
    private String password;
    private String code;
    private String telCode;
    private boolean checked;
}
