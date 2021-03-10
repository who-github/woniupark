package com.woniuxy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TanantVO  extends PageVO {
    private int id;
    private String name;
    private double balance;
    private String address;
    private String tel;
    private int credit;
    private String qq;
    private String email;
    private String head;
}
