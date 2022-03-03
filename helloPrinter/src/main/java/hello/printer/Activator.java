package hello.printer;

import hello.printer.impl.HelloPrinterImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    public ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        HelloPrinter printer = new HelloPrinterImpl();

        registration = bundleContext
                .registerService(HelloPrinter.class.getName(), printer, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }
}
