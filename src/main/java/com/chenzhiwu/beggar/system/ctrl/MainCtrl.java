package com.chenzhiwu.beggar.system.ctrl;

import com.chenzhiwu.beggar.modules.system.service.MenuService;
import com.chenzhiwu.beggar.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Controller
public class MainCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;


    /**
     * 主页
     */
    @GetMapping("/index")
    @RequiresPermissions("index")
    public String index(Model model) {
        return "/system/main/index";
    }

    @GetMapping("/redirect")
    public String redirect(@RequestParam("url") String url) {
        return "redirect:" + url;
    }

    /**
     * 跳转到修改密码页面
     */
    @GetMapping("/editPwd")
    @RequiresPermissions("index")
    public String toEditPwd() {
        return "/system/main/editPwd";
    }


}
