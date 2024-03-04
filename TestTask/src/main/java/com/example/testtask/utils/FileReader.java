package com.example.testtask.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileReader {

    private final MongoDatabase database;

    @Autowired
    public FileReader(MongoDatabase database) {
        this.database = database;
    }

    @Cacheable("fileContent")
    public String readFileContent(String filePath) {
        Path path = Paths.get(filePath);
        try {
            byte[] contentBytes = Files.readAllBytes(path);
            System.out.println("its worked");
            return new String(contentBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void readAndUpdateDB(String filePath) {
        String content = readFileContent(filePath);
        if (content != null) {
            Document newDoc = Document.parse(content);
            MongoCollection<Document> collection = database.getCollection("test");
            Document existingDoc = collection.find().first();

            if (existingDoc != null) {
                newDoc.put("_id", existingDoc.get("_id"));
                collection.replaceOne(existingDoc, newDoc);
            } else {
                collection.insertOne(newDoc);
            }
        }
    }
}
