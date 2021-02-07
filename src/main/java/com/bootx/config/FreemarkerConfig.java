package com.bootx.config;

import com.bootx.util.SystemUtils;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.utility.StandardCompress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreemarkerConfig {

    @Value("${template.datetime_format}")
    private String dateFormat;

    @Value("${template.time_format}")
    private String timeFormat;

    @Value("${template.datetime_format}")
    private String dateTimeFormat;

    @Value("${template.update_delay}")
    private String update_delay;

    @Value("${template.encoding}")
    private String encoding;

    @Value("${template.number_format}")
    private String numberFormat;

    @Value("${template.boolean_format}")
    private String booleanFormat;

    @Value("${template.loader_path}")
    private String templateLoaderPath;

    @Value("${url_escaping_charset}")
    private String urlEscapingCharset;


    @Resource
    private WebApplicationContext webApplicationContext;

    @Bean
    public ShiroTags shiroTags() {
        return new ShiroTags();
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:/", templateLoaderPath);
        freeMarkerConfigurer.setServletContext(webApplicationContext.getServletContext());

        Properties settings = new Properties();
        settings.setProperty("default_encoding", encoding);
        settings.setProperty("url_escaping_charset", urlEscapingCharset);
        settings.setProperty("output_format", "HTMLOutputFormat");
        settings.setProperty("template_update_delay", update_delay);
        settings.setProperty("tag_syntax", "auto_detect");
        settings.setProperty("classic_compatible", "true");
        settings.setProperty("number_format", numberFormat);
        settings.setProperty("boolean_format", booleanFormat);
        settings.setProperty("datetime_format", dateTimeFormat);
        settings.setProperty("date_format", dateFormat);
        settings.setProperty("time_format", timeFormat);
        settings.setProperty("object_wrapper", "freemarker.ext.beans.BeansWrapper");
        freeMarkerConfigurer.setFreemarkerSettings(settings);

        Map<String, Object> variables = new HashMap<String, Object>();

        variables.put("base", "");
        variables.put("showPowered", true);
        variables.put("setting", SystemUtils.getSetting());
        variables.put("shiro", shiroTags());
        variables.put("compress", StandardCompress.INSTANCE);
        freeMarkerConfigurer.setFreemarkerVariables(variables);
        return freeMarkerConfigurer;
    }
}
