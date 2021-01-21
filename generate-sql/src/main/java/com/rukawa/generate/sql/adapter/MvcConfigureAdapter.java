package com.rukawa.generate.sql.adapter;

import com.rukawa.generate.sql.interceptor.TokenUpdateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigureAdapter implements WebMvcConfigurer {

    @Value("${image.location}")
    private String imageLocation;

    @Autowired
    private TokenUpdateInterceptor tokenUpdateInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imageLocation);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenUpdateInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/logout");
    }
}
