package com.liuche.common.util;

import com.liuche.common.enums.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 刘彻
 * @Date 2023/7/24 22:55
 * @PackageName: com.liuche.common.util
 * @ClassName: JsonData
 * @Description: 统一返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {

    /**
     * 状态码 0 表示成功，1表示处理中，-1表示失败
     */

    private Integer code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 描述
     */
    private String msg;


    /**
     * 成功，传入数据
     * @return
     */
    public static JsonData ok() {
        return new JsonData(0, null, null);
    }

    /**
     *  成功，传入数据
     * @param data
     * @return
     */
    public static JsonData ok(Object data) {
        return new JsonData(0, data, null);
    }

    /**
     * 失败，传入描述信息
     * @param msg
     * @return
     */
    public static JsonData error(String msg) {
        return new JsonData(-1, null, msg);
    }


    /**
     * 自定义状态码和错误信息
     * @param code
     * @param msg
     * @return
     */
    public static JsonData error(int code, String msg) {
        return new JsonData(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     * @param codeEnum
     * @return
     */
    public static JsonData buildResult(ExceptionCode codeEnum){
        return JsonData.error(codeEnum.getCode(),codeEnum.getMsg());
    }
}


