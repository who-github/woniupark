package com.woniuxy.vo;

import lombok.Data;

import java.util.List;

@Data
public class SetRoleVO <E> {

    private Integer uid;
    private List<E> checkList;

}
