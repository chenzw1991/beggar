package com.chenzhiwu.beggar.service.impl;

import com.chenzhiwu.beggar.dao.UserDao;
import com.chenzhiwu.beggar.pojo.User;
import com.chenzhiwu.beggar.result.CodeMsg;
import com.chenzhiwu.beggar.service.UserService;
import com.chenzhiwu.beggar.util.MD5Util;
import com.chenzhiwu.beggar.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

/**
 * @author:IGG
 * @date:2019/12/13-09 : 12
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Transactional
    @CachePut(value = "redisCache", key = "'user_'+#id")
    public User getUserById(Long id){
        return userDao.getUserById(id);
    }

    public CodeMsg login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo==null) {
            return CodeMsg.SERVER_ERROR;
        }
        //经过了依次MD5的密码
        String mobile=loginVo.getMobile();
        String formPass=loginVo.getPassword();
        //判断手机号是否存在
        User user= getUserById(Long.parseLong(mobile));
        //查询不到该手机号的用户
        if(user==null) {
            return CodeMsg.MOBILE_NOTEXIST;
        }
        //手机号存在的情况，验证密码，获取数据库里面的密码与salt去验证
        //111111--->e5d22cfc746c7da8da84e0a996e0fffa
        String dbPass=user.getPwd();
        String dbSalt=user.getSalt();
        System.out.println("dbPass:"+dbPass+"   dbSalt:"+dbSalt);
        //验证密码，计算二次MD5出来的pass是否与数据库一致
        String tmppass= MD5Util.formPassToDBPass(formPass, dbSalt);
        System.out.println("formPass:"+formPass);
        System.out.println("tmppass:"+tmppass);
        if(!tmppass.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }
        //生成cookie
//        String token = UUIDUtil.uuid();
//        addCookie(user,token,response);
        return CodeMsg.SUCCESS;

    }
}
