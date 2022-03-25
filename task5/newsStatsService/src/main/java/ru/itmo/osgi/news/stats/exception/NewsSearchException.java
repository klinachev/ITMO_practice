package ru.itmo.osgi.news.stats.exception;

public class NewsSearchException extends Exception {
    public NewsSearchException() {
    }

    public NewsSearchException(String message) {
        super(message);
    }

    public NewsSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsSearchException(Throwable cause) {
        super(cause);
    }
}
