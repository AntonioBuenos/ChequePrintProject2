package by.smirnov.chequeprintproject.config;

import by.smirnov.chequeprintproject.repository.cache.DiscountCardCache;
import by.smirnov.chequeprintproject.repository.cache.LFUCache;
import by.smirnov.chequeprintproject.repository.cache.LRUCache;
import by.smirnov.chequeprintproject.repository.cache.LRULinkedHashMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    private static String cacheType = "LRU";

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
}
