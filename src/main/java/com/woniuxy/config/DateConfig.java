package com.woniuxy.config;

import com.woniuxy.utils.String2Date;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注解类
@Configuration
public class DateConfig implements WebMvcConfigurer {

    // 添加数据转换类型：将string存储为date类型
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new String2Date());
    }

}
