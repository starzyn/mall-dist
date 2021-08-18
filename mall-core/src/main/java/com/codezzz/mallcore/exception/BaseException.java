package com.codezzz.mallcore.exception;

public class BaseException extends RuntimeException {
    private final BaseErrorCode error;
    private final Object[] args;

    public BaseException(BaseErrorCode error) {
        this(error, null);
    }

    public BaseException(BaseErrorCode error, Object... args) {
        super(error.getMessage());
        this.error = error;
        this.args = args;
    }

    public BaseErrorCode getError() {
        return this.error;
    }

    public Object[] getArgs() {
        return this.args;
    }
}
