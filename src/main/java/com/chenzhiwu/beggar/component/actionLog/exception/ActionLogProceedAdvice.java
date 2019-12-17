package com.chenzhiwu.beggar.component.actionLog.exception;

import com.chenzhiwu.beggar.common.exception.advice.ExceptionAdvice;

/**
 * 运行时抛出的异常进行日志记录
 * @author 小懒虫
 * @date 2019/4/6
 */
public class ActionLogProceedAdvice implements ExceptionAdvice {

    @Override
    public void run(RuntimeException e) {}
}
