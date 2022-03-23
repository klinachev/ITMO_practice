package ru.itmo.osgi.hello.printer.impl;

import ru.itmo.osgi.hello.printer.HelloPrinter;

public class HelloPrinterImpl implements HelloPrinter {
    @Override
    public void printHello() {
        System.out.println("Hello OSGi World!");
    }
}
