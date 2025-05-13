package com.polarbookshop.edgeservice.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class WebConfig implements WebFluxConfigurer {
//
//    @Override
//    public void configurePathMatching(PathMatchConfigurer configurer) {
//        configurer.setUseTrailingSlashMatch(true);
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/webjar/META-INF/resources/webjars/")
//                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
//    }
//}
