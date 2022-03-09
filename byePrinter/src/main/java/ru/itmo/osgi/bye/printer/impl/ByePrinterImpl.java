package ru.itmo.osgi.bye.printer.impl;

import org.osgi.service.component.annotations.Component;
import ru.itmo.osgi.bye.printer.ByePrinter;

@Component(service = ByePrinter.class)
public class ByePrinterImpl implements ByePrinter {
    @Override
    public void print() {
        System.out.println("Bye OSGI!");
    }
}
