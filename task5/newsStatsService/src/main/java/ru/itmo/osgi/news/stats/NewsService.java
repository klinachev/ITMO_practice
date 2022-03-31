package ru.itmo.osgi.news.stats;

import ru.itmo.osgi.news.stats.exception.NewsServiceException;

import java.util.List;

public interface NewsService {
    List<String> findNews() throws NewsServiceException;

    String getUrl();

    String commandName();

    String description();
}
