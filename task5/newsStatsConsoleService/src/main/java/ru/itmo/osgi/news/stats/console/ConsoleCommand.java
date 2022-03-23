package ru.itmo.osgi.news.stats.console;

public interface ConsoleCommand {
    void stats();

    void stats(String... names);
}
