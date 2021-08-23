package com.codezzz.mallcore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author felixu
 * @since 2021.05.10
 */
@Getter
@AllArgsConstructor
public enum ErrorGroup {

    GENERAL("通用信息", true),
    USER("用户服务", true)
    ;

    private final String desc;

    private final boolean accessible;
}
