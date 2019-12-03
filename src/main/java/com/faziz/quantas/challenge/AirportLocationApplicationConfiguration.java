package com.faziz.quantas.challenge;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import java.io.File;
import java.io.IOException;
import static java.nio.charset.Charset.forName;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class AirportLocationApplicationConfiguration {
    
    @Value("${quantas.airport.api}")
    private String quantasAirportsApi;
    
    @Value("${airports.data.json.file.name}")
    private String jsonDataFile;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("airports");
    }

    @Bean(name = "airports.json")
    public Resource airportsJsonData() throws IOException {
        return loadDataResource();
    }
    
    @Bean
    public com.jayway.jsonpath.Configuration jsonPathConfiguration() {
        return com.jayway.jsonpath.Configuration.builder().
            jsonProvider(new JacksonJsonProvider()).
                mappingProvider(new JacksonMappingProvider()).build();
    }
    
    @Bean
    public DocumentContext documentContext() throws IOException {
        return JsonPath.parse(airportsJsonData().getInputStream(), jsonPathConfiguration());
    }

    /**
     * Loads the Json data.
     * <br/>
     * First tries to load the Airports Json data from Quantas API, if the API is not available, loads the copy of the data from the classpath.
     * @return
     * @throws IOException 
     */
    private Resource loadDataResource() throws IOException {
        try {
            String json = restTemplate().getForObject(quantasAirportsApi, String.class);
            File jsonFile = createFileIfNotExists();
            Files.write(jsonFile.toPath(), json.getBytes(forName("UTF-8")));
            return new FileSystemResource(jsonFile);
        } catch(RestClientException ex) {
            return new ClassPathResource(jsonDataFile);
        }   
    }

    private File createFileIfNotExists() throws IOException {
        File f = new File("./" + jsonDataFile);
        f.createNewFile();
        return f;
    }
}
