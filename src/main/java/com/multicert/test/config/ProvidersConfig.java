package com.multicert.test.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;


@Slf4j
@Configuration
public class ProvidersConfig {

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean("swagger2Feature")
    public Feature swagger2Feature() {
        Swagger2Feature result = new Swagger2Feature();
        result.setTitle("Spring Boot + CXF + Swagger Example");
        result.setDescription("Spring Boot + CXF + Swagger Example  Generic Api for Clients with multiple countries");
        result.setBasePath("/api");
        result.setVersion("v1");
        result.setContact("Gabriel Almeida");
        result.setSchemes(new String[] { "http"});
        result.setPrettyPrint(true);
        result.setScanAllResources(true);
        result.setUsePathBasedConfig(true);
        return result;
    }


    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
            .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
            .getHandlerMethods();
        map.forEach((key, value) -> log.info("{} {}", key, value));
    }



}
