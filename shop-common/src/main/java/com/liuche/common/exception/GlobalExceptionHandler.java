package com.liuche.common.exception;

import com.liuche.common.util.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 刘彻
 * @Date 2023/7/24 23:01
 * @PackageName: com.liuche.common.exception
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handle(Exception e) {

        if (e instanceof BusinessException) {
            BusinessException bizException = (BusinessException) e;
            log.info("[业务异常]" + e);
            return JsonData.error(bizException.getCode(), bizException.getMsg());
        } else {
            log.info("[系统异常]" + e);
            return JsonData.error("全局异常，未知错误");
        }

    }
}