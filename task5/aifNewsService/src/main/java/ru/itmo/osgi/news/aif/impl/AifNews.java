package ru.itmo.osgi.news.aif.impl;

import org.osgi.service.component.annotations.Component;
import ru.itmo.osgi.news.stats.AbstractNewsStats;
import ru.itmo.osgi.news.stats.NewsStats;

@Component(
        service = NewsStats.class,
        immediate = true
)
public class AifNews extends AbstractNewsStats {
    private static final String AIF_URL = "https://aif.ru/rss/news.php";

    @Override
    protected String url() {
        return AIF_URL;
    }

    @Override
    public String commandName() {
        return "aif";
    }

    @Override
    public String fullServiceName() {
        return "Argumenty i Fakty";
    }
}
