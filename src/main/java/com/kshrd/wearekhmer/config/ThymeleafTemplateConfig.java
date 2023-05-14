package com.kshrd.wearekhmer.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;


@Configuration
public class ThymeleafTemplateConfig {


    @Value("${thymeleaf.templates.location}")
    private String templateLocation;

    @Value("${thymeleaf.templates.extension}")
    private String templateExtension;


    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(springResourceTemplateResolver());
        return templateEngine;
    }


    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {

        SpringResourceTemplateResolver springResourceTemplateResolver
                = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setPrefix(templateLocation);
        springResourceTemplateResolver.setSuffix(templateExtension);
        springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springResourceTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return springResourceTemplateResolver;

    }
}
