package ru.itmo.osgi.news.dp.impl;

import org.osgi.service.component.annotations.Component;
import ru.itmo.osgi.news.stats.search.AbstractNewsStats;
import ru.itmo.osgi.news.stats.search.NewsStats;

@Component(
        property = {
                "type=dpNews"
        },
        service = NewsStats.class,
        immediate = true
)
public class DpNews extends AbstractNewsStats {
    private static final String DP_URL = "https://www.dp.ru/exportnews.xml";

    @Override
    protected String url() {
        return DP_URL;
    }
}
