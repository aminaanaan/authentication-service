package com.example.hotel.configuration;


import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.*;

import java.util.*;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    
	 private final long MAX_AGE_SECS = 3600;

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	        .allowedOrigins("*")
	        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true)
	        .maxAge(MAX_AGE_SECS);
	    }
	    @Bean(name = "localeResolver")
	    public LocaleResolver localeResolver() {
	      SessionLocaleResolver slr = new SessionLocaleResolver();
	      slr.setDefaultLocale(Locale.US);
	      return slr;
	    }
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	      localeChangeInterceptor.setParamName("language");
	      registry.addInterceptor(localeChangeInterceptor);
	    }
}