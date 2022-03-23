package ru.itmo.osgi.hello.command.service.impl;

import org.osgi.service.component.annotations.Component;
import ru.itmo.osgi.hello.command.service.HelloService;


@Component(
        property = {
                "osgi.command.scope=practice",
                "osgi.command.function=hello"
        },
        service = HelloService.class,
        immediate = true
)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "Hello to no one";
    }

    public String hello(String param) {
        return "Hello, " + param;
    }

    @Override
    public String hello(String... param) {
        return "Hello to all of you, " + String.join(", ", param);
    }

}
