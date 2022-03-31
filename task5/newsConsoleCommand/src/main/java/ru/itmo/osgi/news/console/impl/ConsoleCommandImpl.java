package ru.itmo.osgi.news.console.impl;

import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import ru.itmo.osgi.news.console.ConsoleCommand;
import ru.itmo.osgi.news.stats.NewsService;
import ru.itmo.osgi.news.stats.exception.NewsServiceException;

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
@Designate(ocd = ConsoleCommandImpl.CommandConfig.class)
public class ConsoleCommandImpl implements ConsoleCommand {
    private CommandConfig config;

    @ObjectClassDefinition(name = "News stats command config")
    @interface CommandConfig {
        @AttributeDefinition(name = "words count", type = AttributeType.INTEGER)
        int wordsCount() default 10;

        @AttributeDefinition(name = "min word size", type = AttributeType.INTEGER)
        int wordSizeLimit() default 3;
    }

    @Activate
    void activate(CommandConfig config) {
        this.config = config;
    }


    private final Map<String, NewsService> newsStats = new ConcurrentHashMap<>();

    @Reference(
            service = NewsService.class,
            cardinality = ReferenceCardinality.MULTIPLE,
            policy = ReferencePolicy.DYNAMIC
    )
    protected void bindNewsStats(NewsService stats) {
        newsStats.put(stats.commandName(), stats);
    }

    protected void unbindNewsStats(NewsService stats) {
        newsStats.remove(stats.commandName(), stats);
    }

    private void addService(StringBuilder sb, NewsService stats) {
        if (stats == null) {
            return;
        }
        sb.append("command: ")
                .append(stats.commandName())
                .append(", service name: ")
                .append(stats.description())
                .append("\n");
    }

    private String getAvailableServices() {
        StringBuilder sb = new StringBuilder();
        for (NewsService ns : newsStats.values()) {
            addService(sb, ns);
        }
        return sb.toString();
    }

    @Override
    public void stats() {
        String names = getAvailableServices();
        if (names.isEmpty()) {
            System.out.println("There is no available services");
        } else {
            System.out.println("Available services:");
            System.out.println(names);
        }
    }

    private List<String> getWords(List<String> strings) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\wа-яА-Я]+");
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                words.add(matcher.group());
            }
        }
        return words;
    }

    private String getPopularWordsString(List<String> words) {
        return words.stream()
                .filter(s -> s.length() > config.wordSizeLimit())
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.summingInt(x -> 1)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(config.wordsCount())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    public void stats(String arg) {
        NewsService stats = this.newsStats.get(arg);
        if (stats == null) {
            System.out.println("This service is not available");
            return;
        }

        List<String> strings;
        try {
            strings = stats.findNews();
        } catch (NewsServiceException e) {
            System.out.println("Unable to find news by this service");
            return;
        }
        List<String> words = getWords(strings);
        System.out.println(getPopularWordsString(words));
    }

    @Override
    public void stats(String... args) {
        System.out.println("0 or 1 argument expected");
    }

}
