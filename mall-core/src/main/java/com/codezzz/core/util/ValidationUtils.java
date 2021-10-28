package com.codezzz.core.util;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author codezzz
 * @Description: 参数校验的工具类
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
