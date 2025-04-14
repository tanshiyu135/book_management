package com.example.book_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有请求路径
                .allowedOriginPatterns("*") // 允许所有源（注意：Spring Boot 2.4+ 推荐用这个）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true) // 允许携带 cookie
                .allowedHeaders("*")
                .maxAge(3600); // 缓存时间
    }
}
