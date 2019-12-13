package com.chenzhiwu.beggar.exception;

import com.chenzhiwu.beggar.result.CodeMsg;

/**
 * @author:IGG
 * @date:2019/12/13-09 : 36
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private CodeMsg cm;
    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm=cm;

    }
    public CodeMsg getCm() {
        return cm;
    }
    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }

}
