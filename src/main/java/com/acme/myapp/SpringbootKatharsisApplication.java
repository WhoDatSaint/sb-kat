package com.acme.myapp;

import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.spring.boot.KatharsisConfigV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RestController
@SpringBootApplication
//@Import(KatharsisConfigV3.class)
@Import(KatharsisConfigV2.class)
public class SpringbootKatharsisApplication {

    @Autowired
    private ResourceRegistry resourceRegistry;

/*    @RequestMapping("/resourcesInfo")
    public Map<?, ?> getResources() {
        Map<String, String> result = new HashMap<>();
        // Add all resources (i.e. Project and Task)
        for (RegistryEntry entry : resourceRegistry.getResources()) {
            result.put(entry.getResourceInformation().getResourceType(), resourceRegistry.getResourceUrl(entry.getResourceInformation()));
        }
        return result;
    }*/

    @RequestMapping("/resourcesInfo")
    public Map<?, ?> getResources() {
        Map<String, String> result = new HashMap<>();
        // Add all resources (i.e. Project and Task)
        for (Class<?> clazz : resourceRegistry.getResources().keySet()) {
            result.put(resourceRegistry.getResourceType(clazz), resourceRegistry.getResourceUrl(clazz));
        }
        return result;
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKatharsisApplication.class, args);
	}
}
