package com.cxr.other.filterTest;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        System.out.println("@Bean#WebConfiguration");
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new fTest());
        registration.addUrlPatterns("/admin/*");
        /*registration.addInitParameter("paramName", "paramValue");*/
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

}
