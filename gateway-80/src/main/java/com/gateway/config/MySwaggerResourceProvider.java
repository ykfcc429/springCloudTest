package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 聚合各个服务的swagger接口
 */
@Component
@Primary
public class MySwaggerResourceProvider implements SwaggerResourcesProvider {
    /**
     * swagger2默认的url后缀
     */
    private static final String SWAGGER2URL = "/v2/api-docs";
 
    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;
 
    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;
 
    @Autowired
    public MySwaggerResourceProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }
 
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        // 获取所有可用的host：serviceId
        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !self.equals(route.getUri().getHost().toLowerCase()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        routeHosts.stream().distinct().forEach(str->{
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setUrl("/".concat(str.toLowerCase()).concat(SWAGGER2URL));
            swaggerResource.setName(str);
            resources.add(swaggerResource);
        });

        return resources;
    }
}