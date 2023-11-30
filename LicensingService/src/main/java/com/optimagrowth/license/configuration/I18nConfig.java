package com.optimagrowth.license.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nConfig {
    //internalization(setting locale and different languages)
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);  //sets region and language to english
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");  //base file name prefix
        messageSource.setUseCodeAsDefaultMessage(true); //doesn't throw error of no message found it returns method code
        messageSource.setDefaultEncoding("UTF-8");  //to handle non ASCII encoding
        return messageSource;
    }
}
