package ru.itmo.osgi.bye.user;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import ru.itmo.osgi.bye.printer.ByePrinter;

@Component
public class ByeApplier {
    private ByePrinter printer;

    @Reference(service = ByePrinter.class)
    public void setByePrinter(ByePrinter printer) {
        this.printer = printer;
    }

    public void unbindByePrinter(ByePrinter printer) {
        this.printer = null;
    }

    @Deactivate
    public void deactivate() {
        if (printer != null) {
            printer.print();
        }
    }
}
