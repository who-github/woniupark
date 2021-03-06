package com.woniuxy.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component   //必须将当前处理器将由spring容器管理，否则不生效
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始insert填充......");
        this.setFieldValByName("createTime",new Date(),metaObject);
//        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("开始update填充......");
//        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
