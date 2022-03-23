package ru.itmo.osgi.hello.printer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import ru.itmo.osgi.hello.printer.impl.HelloPrinterImpl;

public class Activator implements BundleActivator {
    public ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext bundleContext) {
        HelloPrinter printer = new HelloPrinterImpl();

        registration = bundleContext
                .registerService(HelloPrinter.class.getName(), printer, null);
    }

    @Override
    public void stop(BundleContext bundleContext) {
        registration.unregister();
    }
}
