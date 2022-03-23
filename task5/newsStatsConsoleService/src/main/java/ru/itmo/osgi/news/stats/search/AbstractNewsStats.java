package ru.itmo.osgi.news.stats.search;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractNewsStats implements NewsStats {

    protected abstract String url();

    protected SyndFeed parseFeed(String url) throws IOException {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> findNews() throws IOException {
        try {
            SyndFeed syndFeed = parseFeed(url());
            List<String> titles = new ArrayList<>();
            for (SyndEntry entry : syndFeed.getEntries()) {
                titles.add(entry.getTitle());
            }
            return titles;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
