package com.woniuxy.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 日期类型转换器
public class String2Date implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if (!StringUtils.hasLength(s)){
            throw new RuntimeException("参数不能为空！！！");
        }
        // 转换的类型
        String partten1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        // 转换的类型
        String partten2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
        // 尝试类型的转换
        try {
            if(s.matches(partten1)){
                return simpleDateFormat1.parse(s);
            }else if(s.matches(partten2)){
                return simpleDateFormat2.parse(s);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}
