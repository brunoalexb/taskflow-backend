package com.brunodev.taskflow.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
		        .allowedHeaders("*")  // Permite todos os cabeçalhos
		        .allowCredentials(true);  // Permite envio de cookies/sessões, se necessário
    }
}

