package com.liuche.common.exception;

import com.liuche.common.enums.ExceptionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘彻
 * @Date 2023/7/24 22:57
 * @PackageName: com.liuche.common.exception
 * @ClassName: BusinessException
 * @Description: 自定义异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -7056265188627626207L;
    private Integer code;
    private String msg;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BusinessException(ExceptionCode codeEnum, String msg) {
        super(msg);
        this.code = codeEnum.getCode();
        this.msg = msg;
    }

    public BusinessException(ExceptionCode codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }
}
