package org.example.backend.config;

import org.example.backend.utils.RequestResponseLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestResponseLoggingInterceptor requestResponseLoggingInterceptor;

    public WebConfig(RequestResponseLoggingInterceptor requestResponseLoggingInterceptor) {
        this.requestResponseLoggingInterceptor = requestResponseLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestResponseLoggingInterceptor);
    }
}