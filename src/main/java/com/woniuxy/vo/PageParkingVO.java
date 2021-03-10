package com.woniuxy.vo;

import lombok.Data;

@Data
public class PageParkingVO {
    //当前页数
    private Integer current;
    //每页总条数
    private Integer size;
    //总页数
    private Integer pages;
    //搜索内容
    private String title;
    //车库位置
    private String parkingAddress;
}
