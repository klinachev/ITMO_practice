package ru.itmo.osgi.hello.user;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import ru.itmo.osgi.hello.printer.HelloPrinter;

public class Activator implements BundleActivator {
    private ServiceTracker<?, HelloPrinter> tracker;

    @Override
    public void start(BundleContext bundleContext) {
        tracker = new ServiceTracker<>(bundleContext, HelloPrinter.class.getName(), null);
        tracker.open();

        HelloPrinter printer = tracker.getService();
        if (printer == null) {
            return;
        }
        printer.printHello();
    }

    @Override
    public void stop(BundleContext bundleContext) {
        tracker.close();
    }
}
