package com.kelian.exception;

public class ImageLoadException extends RuntimeException {
    public ImageLoadException(String message) {
        super(message);
    }
    public ImageLoadException(String message, Throwable e) {
        super(message, e);
    }
}
