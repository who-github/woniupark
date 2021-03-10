package com.woniuxy.vo;

import lombok.Data;

import java.util.Date;

@Data
public class HandleNoPutawayVo {
    private Integer id;
    private String title;
    private Integer parkingNumber;
    private String parkingAddress;
    private String ParkingArea;
    private String rentalType;
    private String rentalPrice;
    private Integer parkingStatus;
}
