package ru.itmo.osgi.bye.printer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

public interface ByePrinter {
    @Reference
    void print();
}
