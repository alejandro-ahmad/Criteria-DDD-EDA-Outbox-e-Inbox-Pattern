package com.example.demo.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfig implements WebMvcConfigurer {

    private static final String VERSIONED_PATH_PREFIX = "/api/";

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .usePathSegment(1, path -> {
                    String value = path.pathWithinApplication().value();
                    return value.startsWith(VERSIONED_PATH_PREFIX) && value.length() > VERSIONED_PATH_PREFIX.length();
                })
                .setVersionParser(new VPrefixedIntegerApiVersionParser())
                .setVersionRequired(false)
                .addSupportedVersions("1", "2")
                .detectSupportedVersions(true);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8081", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
