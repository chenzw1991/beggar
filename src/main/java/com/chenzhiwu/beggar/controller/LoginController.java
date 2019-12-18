package com.chenzhiwu.beggar.controller;

import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.result.Result;
import com.chenzhiwu.beggar.service.BeggarUserService;
import com.chenzhiwu.beggar.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author:IGG
 * @date:2019/12/13-09 : 31
 */
@RequestMapping("/login")
@Controller
public class LoginController {
    @Autowired
    private BeggarUserService beggarUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        System.out.println("longin.........");
        return "login";//返回页面login
    }

    //使用JSR303校验
    @RequestMapping("/do_login")//作为异步操作
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletRequest request, @Valid LoginVo loginVo) {//0代表成功
        //参数检验成功之后，登录
        CodeMsg cm= beggarUserService.login(request,loginVo);
        if(cm.getCode()==0) {
            return Result.success(true);
        }else {
            return Result.error(cm);
        }
    }
}
