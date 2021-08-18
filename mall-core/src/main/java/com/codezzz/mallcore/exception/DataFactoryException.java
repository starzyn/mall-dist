package com.codezzz.mallcore.exception;

import lombok.Getter;

/**
 * @author felixu
 * @since 2021.05.10
 */
@Getter
public class DataFactoryException extends BaseException {

    private final ErrorCode error;

    private final Object[] args;

    public DataFactoryException(ErrorCode error) {
        this(error, (Object) null);
    }

    public DataFactoryException(ErrorCode error, Object... args) {
        super(error, args);
        this.error = error;
        this.args = args;
    }
}
