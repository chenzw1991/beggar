package com.chenzhiwu.beggar.result;

/**
 * @author:IGG
 * @date:2019/12/12-10 : 30
 * 成功的时候，只返回数据。
 * 失败的话，那么就返回CodeMsg。（里面包含错误码以及错误信息）
 *
 */
public class CodeMsg {
    private int code;
    private String msg;
    //通用异常
    public static CodeMsg SUCCESS=new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR=new CodeMsg(500100,"服务端异常!");
    public static CodeMsg BIND_ERROR=new CodeMsg(500101,"参数校验异常:%s");
    //登录模块异常
    public static CodeMsg MOBILE_NOTEXIST=new CodeMsg(500214,"手机号号码不存在!");
    public static CodeMsg PASSWORD_ERROR=new CodeMsg(500215,"密码错误!");

    //商品模块异常
    public static CodeMsg MIAOSHA_OVER_ERROR=new CodeMsg(500500,"商品库存不足!");
    public static CodeMsg REPEATE_MIAOSHA=new CodeMsg(500500,"不能重复秒杀!");
    public static CodeMsg GOODS_DOWNSHELF=new CodeMsg(500500,"商品已下架!");
    public static CodeMsg GOODS_UPSHELF=new CodeMsg(500500,"商品未上架!");

    public CodeMsg(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    //返回一个带参数的错误码
    public CodeMsg fillArgs(Object...args) {//变参
        int code=this.code;
        //message是填充了参数的message
        String message=String.format(this.msg, args);
        return new CodeMsg(code,message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}

