package ru.itmo.osgi.news.console.impl;

import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import ru.itmo.osgi.news.console.ConsoleCommand;
import ru.itmo.osgi.news.stats.NewsStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component(
        service = ConsoleCommand.class,
        immediate = true
)
@GogoCommand(scope = "news", function = "stats")
public class ConsoleCommandImpl implements ConsoleCommand {
    private final Map<String, NewsStats> newsStats = new ConcurrentHashMap<>();

    @Reference(
            service = NewsStats.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC
    )
    protected void bindNewsStats(NewsStats stats) {
        newsStats.put(stats.commandName(), stats);
    }

    protected void unbindNewsStats(NewsStats stats) {
        newsStats.remove(stats.commandName());
    }

    private void addService(StringBuilder sb, NewsStats stats) {
        if (stats == null) {
            return;
        }
        sb.append("command: ")
                .append(stats.commandName())
                .append(", service name: ")
                .append(stats.fullServiceName())
                .append("\n");
    }

    private String availableServices() {
        StringBuilder sb = new StringBuilder();
        for (NewsStats ns : newsStats.values()) {
            addService(sb, ns);
        }
        return sb.toString();
    }

    @Override
    public void stats() {
        String names = availableServices();
        if (names.isEmpty()) {
            System.out.println("There is no available services");
        } else {
            System.out.println("Available services:");
            System.out.println(names);
        }
    }

    public void stats(String arg) {
        NewsStats stats = this.newsStats.get(arg);
        if (stats == null) {
            System.out.println("This service is not available");
            return;
        }

        List<String> strings;
        try {
            strings = stats.findNews();
        } catch (IOException e) {
            System.out.println("Network request failed");
            return;
        } catch (RuntimeException e) {
            System.out.println("Unable to find news by this service");
            return;
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\wа-яА-Я]+");
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                words.add(matcher.group());
            }
        }
        String popularWords = words.stream()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.summingInt(x -> 1)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
//                .map(x -> x.getKey() + "=" + x.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
        System.out.println(popularWords);
    }

    @Override
    public void stats(String... args) {
        System.out.println("0 or 1 argument expected");
    }

}
