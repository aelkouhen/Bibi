package com.carhub.api.proxy.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("Lyne API - Vehicle foundation service", "/auto/v2/api-docs", "1.0"));
        resources.add(swaggerResource("Mima API - Authentication / Authorization service", "/auth/v2/api-docs", "1.0"));
        resources.add(swaggerResource("Mino API - Media management service", "/media/v2/api-docs", "1.0"));
        resources.add(swaggerResource("Rana API - Social activities service", "/social/v2/api-docs", "1.0"));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}