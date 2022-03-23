package ru.itmo.osgi.news.stats.console.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import ru.itmo.osgi.news.stats.console.ConsoleCommand;
import ru.itmo.osgi.news.stats.search.NewsStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component(
        property = {
                "osgi.command.scope=news",
                "osgi.command.function=stats"
        },
        service = ConsoleCommand.class,
        immediate = true
)
public class ConsoleCommandImpl implements ConsoleCommand {

    private volatile NewsStats lenta, aif, dp;

    @Reference(
            service = NewsStats.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            target ="(type=lentaNews)"
    )
    protected void bindLentaStats(NewsStats stats) {
        this.lenta = stats;
    }

    protected void unbindLentaStats(NewsStats ignored) {
        this.lenta = null;
    }

    @Reference(
            service = NewsStats.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            target ="(type=aifNews)"
    )
    protected void bindAifStats(NewsStats stats) {
        this.aif = stats;
    }

    protected void unbindAifStats(NewsStats ignored) {
        this.aif = null;
    }

    @Reference(
            service = NewsStats.class,
            cardinality = ReferenceCardinality.OPTIONAL,
            target ="(type=dpNews)"
    )
    protected void bindDpStats(NewsStats stats) {
        this.dp = stats;
    }

    protected void unbindDpStats(NewsStats ignored) {
        this.dp = null;
    }

    @Override
    public void stats() {
        System.out.println("1 argument expected");
    }

    public void stats(String arg) {
        NewsStats newsStats = null;
        switch (arg) {
            case "lenta":
                newsStats = lenta;
                break;
            case "aif":
                newsStats = aif;
                break;
            case "dp":
                newsStats = dp;
                break;
            default:
                System.out.println("Invalid service name");
                return;
        }

        if (newsStats == null) {
            System.out.println("This service is not available");
            return;
        }
        List<String> strings;
        try {
            strings = newsStats.findNews();
        } catch (IOException e) {
            System.out.println("Operation failed");
            return;
        } catch (RuntimeException e) {
            System.out.println("Unable to find news by this service");
            return;
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\w+");
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                words.add(matcher.group());
            }
        }
        List<String> popularWords = words.stream()
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(popularWords);
    }

    @Override
    public void stats(String... args) {
        System.out.println("1 argument expected");
    }

}
