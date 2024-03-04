package com.example.testtask.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private final FileReader fileReader;

    public ScheduledTask(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Scheduled(fixedRate = 20000) //is not a fact
    public void updateDatabase() {
        String filePath = "src/main/resources/static/test_report.json";
        fileReader.readAndUpdateDB(filePath);
        System.out.println("yes");
    }
}
