package hello.printer.impl;

import hello.printer.HelloPrinter;

public class HelloPrinterImpl implements HelloPrinter {
    @Override
    public void printHello() {
        System.out.println("Hello OSGi World!");
    }
}
