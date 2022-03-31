package ru.itmo.osgi.news.stats.exception;

public class NewsServiceException extends Exception {
    public NewsServiceException() {
    }

    public NewsServiceException(String message) {
        super(message);
    }

    public NewsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsServiceException(Throwable cause) {
        super(cause);
    }
}
