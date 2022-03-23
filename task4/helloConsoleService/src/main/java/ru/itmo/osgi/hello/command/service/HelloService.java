package ru.itmo.osgi.hello.command.service;

public interface HelloService {
    String hello();

    String hello(String... names);
}
