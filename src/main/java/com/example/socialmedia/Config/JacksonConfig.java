package com.example.socialmedia.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(){
        return new Jackson2ObjectMapperBuilder()
        .failOnUnknownProperties(true);
    }
}
