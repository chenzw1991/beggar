package com.chenzhiwu.beggar.util;/*
@author:Administrator
@date:2019/12/12 22 25
*/

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    final String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    private boolean required=false;
    public void initialize(IsMobile constraintAnnotation) {
        constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required) {//查看值是否是必须的
            if(value.length() != 11){
                return false;
            }
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            boolean isMatch = m.matches();
            if(isMatch){
                return true;
            } else {
                return false;
            }
        }else {
            if(StringUtils.isEmpty(value)) {//required
                return true;
            }else {
                if(value.length() != 11){
                    return false;
                }else {
                    return true;
                }
            }
        }
    }

}
