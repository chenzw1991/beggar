package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.User;
import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author:IGG
 * @date:2019/12/13-09 : 11
 */
public interface UserService {
    public User getUserById(Long id);
    public CodeMsg login(HttpServletResponse response, LoginVo loginVo);
}
