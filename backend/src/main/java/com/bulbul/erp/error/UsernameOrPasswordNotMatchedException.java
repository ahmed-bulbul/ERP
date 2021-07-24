package com.bulbul.erp.error;

public class UsernameOrPasswordNotMatchedException extends Exception{

    public UsernameOrPasswordNotMatchedException() {
        super();
    }

    public UsernameOrPasswordNotMatchedException(String message) {
        super(message);
    }

    public UsernameOrPasswordNotMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameOrPasswordNotMatchedException(Throwable cause) {
        super(cause);
    }

    protected UsernameOrPasswordNotMatchedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
