package com.chenzhiwu.beggar.vo;/*
@author:Administrator
@date:2019/12/12 22 21
*/

import com.chenzhiwu.beggar.util.IsMobile;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

public class LoginVo {
    private String mobile;
    private String password;

    @NotNull
    @IsMobile
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @NotNull
    @Length(min=32)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
