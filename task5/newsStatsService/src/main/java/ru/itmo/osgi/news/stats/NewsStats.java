package ru.itmo.osgi.news.stats;

import ru.itmo.osgi.news.stats.exception.NewsSearchException;

import java.util.List;

public interface NewsStats {
    List<String> findNews() throws NewsSearchException;

    String commandName();

    String fullServiceName();
}
