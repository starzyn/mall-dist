package com.codezzz.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseErrorCode {

    /*-------------------------------- 成功 ------------------------------*/
    OK(0, "成功", ErrorGroup.GENERAL),

    /*-------------------------------- 失败(大多为没有被处理到的特殊异常) ------------------------------*/
    FAIL(-1, "出现未知错误，请稍后再试", ErrorGroup.GENERAL),
    MISSING_CODE(-2, "当前错误信息: {0}，缺失真正 code，请联系开发者处理", ErrorGroup.GENERAL),

    /*-------------------------------- 通用异常 ------------------------------*/
    PARAM_ERROR(100, "您提交的数据不符合要求", ErrorGroup.GENERAL),
    LOGIN_REQUIRED(101, "请(重新)登录", ErrorGroup.GENERAL),
    AUTHORIZATION_REQUIRED(102, "您没有执行该操作的权限", ErrorGroup.GENERAL),
    AUTHENTICATION_FAILED(112, "请登陆", ErrorGroup.GENERAL),
    MISSING_SERVLET_REQUEST_PARAMETER(103, "不能为空", ErrorGroup.GENERAL),
    PATH_PARAM_DATA_TYPE_ERROR(104, "路径参数数据类型出错", ErrorGroup.GENERAL),
    INPUT_PARAM_ILLEGAL(105, "入参非法,请检查输入参数或格式", ErrorGroup.GENERAL),
    LICENSE_EXCEED_LIMIT(106, "{0}超出授权限制，请联系license升级服务", ErrorGroup.GENERAL),
    FILE_TYPE_NOT_SUPPORT(107, "文件类型不支持", ErrorGroup.GENERAL),
    FILE_UPLOAD_FAILED(108, "文件上传失败", ErrorGroup.GENERAL),
    FILE_SIZE_EXCEED_LIMIT(109, "文件大小超出限制", ErrorGroup.GENERAL),
    RESOURCE_NOT_FOUND(110, "要查询的资源不存在", ErrorGroup.GENERAL),
    EXCEL_PARSE_ERROR(111, "Excel文件解析失败", ErrorGroup.GENERAL),

    /******************************* 用户异常 **********************************/

    USER_NOT_FOUND(200, "用户不存在或密码错误", ErrorGroup.USER),
    TOKEN_VALID(201, "token 校验失败", ErrorGroup.USER),
    TOKEN_GENERATE_FAILED(202, "token 生成失败", ErrorGroup.USER),
    TOKEN_PARSE_FAILED(203, "token 解析失败", ErrorGroup.USER)
    ;

    /**
     * 返回的 code，非 0 表示错误
     */
    private final int code;

    /**
     * 发生错误时的描述
     */
    private final String message;

    /**
     * 错误码分组
     */
    private final ErrorGroup group;
}
