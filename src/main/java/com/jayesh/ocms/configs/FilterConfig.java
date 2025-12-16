package com.jayesh.ocms.configs;

import com.jayesh.ocms.filters.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final LoggingFilter loggingFilter;

    public FilterConfig(LoggingFilter loggingFilter) {
        this.loggingFilter = loggingFilter;
    }

    @Bean
    FilterRegistrationBean<LoggingFilter> loggingFilterFilterRegistrationBean() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(loggingFilter);
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
