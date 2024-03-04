package com.example.testtask;

import com.example.testtask.utils.FileReader;
import com.example.testtask.utils.ScheduledTask;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class TestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestTaskApplication.class, args);
    }
    @Bean
    public MongoDatabase mongoDatabase() {
        String connectionString = "mongodb://localhost:27017";
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("test");
    }
    @Bean
    public ScheduledTask scheduledTask(FileReader fileReader) {
        return new ScheduledTask(fileReader);
    }
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("fileContent");
    }
}
