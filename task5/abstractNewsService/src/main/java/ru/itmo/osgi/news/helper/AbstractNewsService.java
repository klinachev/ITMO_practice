package ru.itmo.osgi.news.helper;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import ru.itmo.osgi.news.stats.NewsService;
import ru.itmo.osgi.news.stats.exception.NewsServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNewsService implements NewsService {
    protected volatile String url, description;

    protected void setFields(String url, String description) {
        this.url = url;
        this.description = description;
    }

    protected SyndFeed parseFeed(String url) throws NewsServiceException {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException | IOException e) {
            throw new NewsServiceException(e);
        }
    }

    @Override
    public List<String> findNews() throws NewsServiceException {
        SyndFeed syndFeed = parseFeed(getUrl());
        List<String> titles = new ArrayList<>();
        for (SyndEntry entry : syndFeed.getEntries()) {
            titles.add(entry.getTitle());
        }
        return titles;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String description() {
        return description;
    }
}
