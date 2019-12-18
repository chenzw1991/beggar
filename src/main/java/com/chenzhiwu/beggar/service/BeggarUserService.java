package com.chenzhiwu.beggar.service;

import com.chenzhiwu.beggar.pojo.BeggarUser;
import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:IGG
 * @date:2019/12/13-09 : 11
 */
public interface BeggarUserService {
    public BeggarUser getUserById(Long id);
    public CodeMsg login(HttpServletRequest request, LoginVo loginVo);
}
