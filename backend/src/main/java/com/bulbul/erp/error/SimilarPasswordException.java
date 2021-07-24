package com.bulbul.erp.error;

public class SimilarPasswordException extends Exception{
    public SimilarPasswordException() {
        super();
    }

    public SimilarPasswordException(String message) {
        super(message);
    }

    public SimilarPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimilarPasswordException(Throwable cause) {
        super(cause);
    }

    protected SimilarPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
