package com.codezzz.mallcore.util;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import lombok.experimental.UtilityClass;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author codezzz
 * @Description:
 * @date 2021/8/23 14:27
 */
@UtilityClass
public class ValidationUtils {

    private static final String TEMPLATE = "%s : %s\n";

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public static void validate(Object o, Class... classes) {
        Set<ConstraintViolation<Object>> res = validator.validate(o, classes);
        if (res == null || res.isEmpty()) {
            return;
        } else {
            StringBuilder builder = new StringBuilder();
            Iterator<ConstraintViolation<Object>> it = res.iterator();
            while (it.hasNext()) {
                ConstraintViolation<Object> constraintViolation = it.next();
                builder.append(String.format(TEMPLATE, constraintViolation.getPropertyPath() ,constraintViolation.getMessage()));
            }
            throw new IllegalArgumentException(builder.toString());
        }
    }
}
