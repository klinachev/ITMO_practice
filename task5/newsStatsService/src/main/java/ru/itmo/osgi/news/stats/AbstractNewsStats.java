package ru.itmo.osgi.news.stats;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import ru.itmo.osgi.news.stats.exception.NewsSearchException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNewsStats implements NewsStats {

    protected abstract String url();

    protected SyndFeed parseFeed(String url) throws NewsSearchException {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException | IOException e) {
            throw new NewsSearchException(e);
        }
    }

    @Override
    public List<String> findNews() throws NewsSearchException {
        SyndFeed syndFeed = parseFeed(url());
        List<String> titles = new ArrayList<>();
        for (SyndEntry entry : syndFeed.getEntries()) {
            titles.add(entry.getTitle());
        }
        return titles;
    }
}
