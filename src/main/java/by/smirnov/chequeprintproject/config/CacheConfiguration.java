package by.smirnov.chequeprintproject.config;

import by.smirnov.chequeprintproject.repository.cache.DiscountCardCache;
import by.smirnov.chequeprintproject.repository.cache.LFUCache;
import by.smirnov.chequeprintproject.repository.cache.LRUCache;
import by.smirnov.chequeprintproject.repository.cache.LRULinkedHashMapCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
public class CacheConfiguration {

    @Value("${super-cache.type}")
    private String cacheType;

    @Value("${super-cache.limit}")
    private int cacheLimit;

    private static int cacheLimitStatic;

    @Value("${super-cache.limit}")
    private void setCacheLimitStatic(int cacheLimit){
        CacheConfiguration.cacheLimitStatic = cacheLimit;
    }

    @Bean
    public DiscountCardCache initCache(){
        DiscountCardCache cache;
        switch (cacheType){
            case("LRU") -> cache = new LRUCache();
            case("LFU") -> cache = new LFUCache();
            case("LRU_LHM") -> cache = new LRULinkedHashMapCache();
            default -> cache = new LFUCache();
        }
        return cache;
    }

    public static int getCacheLimit(){
        return cacheLimitStatic;
    }
}
