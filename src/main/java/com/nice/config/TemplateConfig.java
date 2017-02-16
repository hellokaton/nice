package com.nice.config;

import com.nice.ext.Funcs;
import com.blade.annotation.Order;
import com.blade.config.BaseConfig;
import com.blade.config.Configuration;
import com.blade.ioc.annotation.Component;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import jetbrick.template.JetGlobalContext;
import jetbrick.template.resolver.GlobalResolver;

@Component
@Order(sort = 2)
public class TemplateConfig implements BaseConfig {

    @Override
    public void config(Configuration configuration) {
        JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
        JetGlobalContext context = templateEngine.getGlobalContext();
        GlobalResolver resolver = templateEngine.getGlobalResolver();
        resolver.registerFunctions(Funcs.class);
        String img_url = configuration.config().get("app.img_url");
        String site_url = configuration.config().get("app.site_url");
        context.set("img_url", img_url);
        context.set("site_url", site_url);
        ViewSettings.$().templateEngine(templateEngine);
    }

}
