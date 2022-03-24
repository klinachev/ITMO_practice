package ru.itmo.osgi.news.stats;

import java.io.IOException;
import java.util.List;

public interface NewsStats {
    List<String> findNews() throws IOException;

    String commandName();

    String fullServiceName();
}
