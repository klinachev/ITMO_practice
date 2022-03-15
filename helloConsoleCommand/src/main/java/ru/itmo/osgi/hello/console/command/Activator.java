package ru.itmo.osgi.hello.console.command;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.Dictionary;
import java.util.Hashtable;

@Component
public class Activator {

    @Activate
    public void activate(BundleContext context) {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("osgi.command.scope", "practice");
        props.put("osgi.command.function", new String[]{"hello"});
        context.registerService(HelloCommand.class, new HelloCommand(), props);
    }
}
