package com.codezzz.core.model.dto;

import com.codezzz.core.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 *  @author: zhan9yn
 *  @Date: 2021/9/28 11:42 上午
 *  @Description: 统一接口返回包装类
 */
public class RespDTO<T> {

    /**
     * 返回的 code 码
     */
    private int code;

    /**
     * 发生错误时的错误信息
     */
    private String message;

    /**
     * 成功返回时的真正数据集
     */
    private T data;

    /**
     * 成功返回且无任何数据返回
     */
    public static <T> RespDTO<T> onSuc() {
        return onSuc(null);
    }

    /**
     * 成功返回且需要传入真正被返回的数据
     */
    public static <T> RespDTO<T> onSuc(T data) {
        return build(0, "请求成功", data);
    }

    /**
     * 错误返回时，放入错误类型的枚举
     */
    public static <T> RespDTO<T> onFail(BaseErrorCode error) {
        return onFail(error.getCode(), error.getMessage());
    }

    /**
     * 错误返回时，放入错误的 code 码以及错误信息
     */
    public static <T> RespDTO<T> onFail(int code, String message) {
        return build(code, message, null);
    }


    private static <T> RespDTO<T> build(int code, String message, T data) {
        return new RespDTO<>(code, message, data);
    }
}