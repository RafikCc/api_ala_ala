package com.merchant.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";
	
    @Bean
    public Docket api() {
        List<SecurityContext> contexts = new ArrayList<>();
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(apiKey());
        contexts.add(securityContext());
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.merchant.api.controller"))
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
                .securityContexts(contexts)
                .securitySchemes(securitySchemes)
                .apiInfo(apiInformation());
    }
    
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = 
                new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> references = new ArrayList<>();
        references.add(new SecurityReference("JWT", authorizationScopes));
        return references;
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }
    
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    
    private ApiInfo apiInformation() {
        return new ApiInfoBuilder()
                .title("Merchant Test API")
                .description("Api merchant untuk test empower front-end API")
                //.contact("")
                .version("1.0.0")
                .build();
    }
}
