package com.example.testtask.controllers;

import com.example.testtask.services.DataService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.bson.Document;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getDataBetween(
            @RequestParam("firstDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
            @RequestParam("lastDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastDate) {
        List<Document> data = dataService.getDataBetween(firstDate, lastDate);
        return ResponseEntity.ok(data);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllData() {
        List<Document> data = dataService.getAllData();
        return ResponseEntity.ok(data);
    }
    @GetMapping("/detailsByAsin")
    public ResponseEntity<List<Document>> getDetailsByAsin() {
        List<Document> data = dataService.getDetailsByAsin();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/byAsins")
    public ResponseEntity<List<Document>> getDataByAsins(@RequestParam List<String> asins) {
        List<Document> data = dataService.getDataByAsins(asins);
        return ResponseEntity.ok(data);
    }


}
