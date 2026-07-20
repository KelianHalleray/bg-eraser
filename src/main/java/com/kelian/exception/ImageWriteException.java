package com.kelian.exception;

public class ImageWriteException extends RuntimeException {
    public ImageWriteException(String message) {
        super(message);
    }
    public ImageWriteException(String message, Throwable e) {
        super(message, e);
    }
}
