package ru.itmo.osgi.bye.user;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import ru.itmo.osgi.bye.printer.ByePrinter;

@Component
public class ByeApplier {
    @Deactivate
    public void deactivate(ComponentContext context) {
        BundleContext bundleContext = context.getBundleContext();
        ServiceReference<?> serviceReference =
                bundleContext.getServiceReference(ByePrinter.class.getName());
        ByePrinter printer = (ByePrinter) bundleContext.getService(serviceReference);
        if (printer == null) {
            return;
        }
        printer.print();
    }
}
