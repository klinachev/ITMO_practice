package ru.itmo.osgi.news.lenta.impl;


import org.osgi.service.component.annotations.Component;
import ru.itmo.osgi.news.stats.search.AbstractNewsStats;
import ru.itmo.osgi.news.stats.search.NewsStats;

@Component(
        property = {
                "type=lentaNews"
        },
        service = NewsStats.class,
        immediate = true
)
public class LentaNews extends AbstractNewsStats {

    private static final String LENTA_URL = "https://api.lenta.ru/rss";

    @Override
    protected String url() {
        return LENTA_URL;
    }
}
