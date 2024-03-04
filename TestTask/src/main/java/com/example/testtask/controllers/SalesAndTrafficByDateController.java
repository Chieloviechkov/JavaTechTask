package com.example.testtask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalesAndTrafficByDateController {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SalesAndTrafficByDateController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/salesAndTraffic")
    public String getSalesAndTraffic(Model model) {
        model.addAttribute("salesAndTrafficByDate", mongoTemplate.findAll(Object.class, "test"));
        return "salesAndTrafficTemplate";
    }
}
