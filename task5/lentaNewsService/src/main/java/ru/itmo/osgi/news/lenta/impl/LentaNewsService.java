package ru.itmo.osgi.news.lenta.impl;


import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import ru.itmo.osgi.news.helper.AbstractNewsService;
import ru.itmo.osgi.news.stats.NewsService;

@Component(
        service = NewsService.class,
        immediate = true
)
@Designate(ocd = LentaNewsService.LentaConfig.class)
public class LentaNewsService extends AbstractNewsService {
    private final static String COMMAND_NAME = "lenta";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @ObjectClassDefinition(name = "lenta news config")
    @interface LentaConfig {
        @AttributeDefinition(name = "news url")
        String url() default "https://api.lenta.ru/rss";

        @AttributeDefinition(name = "command description")
        String description() default "Lenta.ru";
    }

    private void setFields(LentaConfig config) {
        setFields(config.url(), config.description());
    }

    @Activate
    void activate(LentaConfig config) {
        setFields(config);
    }

    @Modified
    void modified(LentaConfig config) {
        setFields(config);
    }
}
