package ru.itmo.osgi.news.console;

public interface ConsoleCommand {
    void stats();

    void stats(String... names);
}
