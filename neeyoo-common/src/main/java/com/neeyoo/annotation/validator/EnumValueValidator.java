package com.neeyoo.annotation.validator;

import com.neeyoo.annotation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 自定义校验注解实现-数组内容校验
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private String[] strValues;
    private int[] intValues;
    private long[] longValues;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        strValues = constraintAnnotation.strValues();
        intValues = constraintAnnotation.intValues();
        longValues = constraintAnnotation.longValues();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            for (String s : strValues) {
                if (s.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer) {
            for (Integer s : intValues) {
                if (s == value) {
                    return true;
                }
            }
        } else if (value instanceof Long) {
            for (Long s : longValues) {
                if (s == value) {
                    return true;
                }
            }
        }
        return false;
    }
}
