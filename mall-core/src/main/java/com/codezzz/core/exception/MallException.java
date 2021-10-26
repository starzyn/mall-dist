package com.codezzz.core.exception;

import lombok.Getter;

/**
 * @author felixu
 * @since 2021.05.10
 */
@Getter
public class MallException extends BaseException {

    private final ErrorCode error;

    private final Object[] args;

    public MallException(ErrorCode error) {
        this(error, (Object) null);
    }

    public MallException(ErrorCode error, Object... args) {
        super(error, args);
        this.error = error;
        this.args = args;
    }
}
