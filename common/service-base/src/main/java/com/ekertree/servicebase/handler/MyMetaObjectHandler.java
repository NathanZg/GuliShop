package com.ekertree.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: MyMetaObjectHandler
 * Description:
 * date: 2022/6/20 14:31
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObj) {
        this.setFieldValByName("gmtCreate", new Date(), metaObj);
        this.setFieldValByName("gmtModified", new Date(), metaObj);
    }
    @Override
    public void updateFill(MetaObject metaObj) {
        this.setFieldValByName("gmtModified", new Date(), metaObj);
    }
}
