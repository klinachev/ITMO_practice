package ru.itmo.osgi.news.aif.impl;

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
@Designate(ocd = AifNewsService.AifConfig.class)
public class AifNewsService extends AbstractNewsService {
    private final static String COMMAND_NAME = "aif";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @ObjectClassDefinition(name = "aif news config")
    @interface AifConfig {
        @AttributeDefinition(name = "news url")
        String url() default "https://aif.ru/rss/news.php";

        @AttributeDefinition(name = "command description")
        String description() default "Argumenty i Fakty";
    }

    private void setFields(AifConfig config) {
        setFields(config.url(), config.description());
    }

    @Activate
    void activate(AifConfig config) {
        setFields(config);
    }

    @Modified
    void modified(AifConfig config) {
        setFields(config);
    }
}
