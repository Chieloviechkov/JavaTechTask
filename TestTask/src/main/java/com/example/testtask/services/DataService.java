package com.example.testtask.services;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DataService {

    private final MongoTemplate mongoTemplate;

    public DataService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Document> getDataBetween(LocalDate startDate, LocalDate endDate) {
        Criteria dateCriteria = Criteria.where("salesAndTrafficByDate.date")
                .gte(startDate.toString())
                .lte(endDate.toString());

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByDate"),
                Aggregation.match(dateCriteria),
                Aggregation.project()
                        .andExclude("_id")
                        .andInclude("salesAndTrafficByDate")
        );


        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "test", Document.class);

        return results.getMappedResults();
    }
    public List<Document> getAllData() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByDate"),
                Aggregation.project()
                        .andExclude("_id")
                        .andInclude("salesAndTrafficByDate")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "test", Document.class);

        return results.getMappedResults();
    }
    public List<Document> getDetailsByAsin() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByAsin"),
                Aggregation.group("salesAndTrafficByAsin.parentAsin")
                        .push("salesAndTrafficByAsin").as("details"),
                Aggregation.project()
                        .andExclude("_id")
                        .andInclude("details")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "test", Document.class);

        return results.getMappedResults();
    }
    public List<Document> getDataByAsins(List<String> asins) {
        Criteria asinCriteria = Criteria.where("salesAndTrafficByAsin.parentAsin").in(asins);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(asinCriteria),
                Aggregation.unwind("$salesAndTrafficByAsin"),
                Aggregation.match(asinCriteria),
                Aggregation.replaceRoot("$salesAndTrafficByAsin")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "test", Document.class);

        return results.getMappedResults();
    }



}
