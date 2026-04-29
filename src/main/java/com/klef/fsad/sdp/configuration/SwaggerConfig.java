package com.klef.fsad.sdp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig 
{
    @Bean
    public OpenAPI apiInfo() 
   {
        return new OpenAPI()
                .info(new Info()
                        .title("Service Management System")
                        .version("1.0")
                        .description("Sample Description Here"));
    }
}
