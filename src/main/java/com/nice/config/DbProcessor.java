package com.nice.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.jdbc.Base;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import com.nice.ext.Funcs;
import jetbrick.template.JetGlobalContext;
import jetbrick.template.resolver.GlobalResolver;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库支持
 *
 * @author biezhi
 * 18/06/2017
 */
public class DbProcessor implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        InputStream in    = DbProcessor.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties  props = new Properties();
        try {
            props.load(in);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(props);
            Base.open(dataSource);

            JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
            JetGlobalContext       context        = templateEngine.getGlobalContext();
            GlobalResolver         resolver       = templateEngine.getGlobalResolver();
            resolver.registerFunctions(Funcs.class);
            blade.templateEngine(templateEngine);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}