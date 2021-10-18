package com.codezzz.malluser.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.codezzz.malluser.constant.UserConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/24 13:47
 */
@Component
public class AutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createdAt", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("status", UserConstant.STATUS_NORMAL, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
