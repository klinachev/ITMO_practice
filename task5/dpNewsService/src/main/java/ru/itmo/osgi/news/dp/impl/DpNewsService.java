package ru.itmo.osgi.news.dp.impl;

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
@Designate(ocd = DpNewsService.DpConfig.class)
public class DpNewsService extends AbstractNewsService {
    private final static String COMMAND_NAME = "dp";

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @ObjectClassDefinition(name = "dp news config")
    @interface DpConfig {
        @AttributeDefinition(name = "news url")
        String url() default "https://www.dp.ru/exportnews.xml";

        @AttributeDefinition(name = "command description")
        String description() default "Delovoy Peterburg";
    }

    private void setFields(DpConfig config) {
        setFields(config.url(), config.description());
    }

    @Activate
    void activate(DpConfig config) {
        setFields(config);
    }

    @Modified
    void modified(DpConfig config) {
        setFields(config);
    }
}
