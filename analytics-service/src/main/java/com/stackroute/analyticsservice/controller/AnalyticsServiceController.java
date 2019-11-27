package com.stackroute.analyticsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.analyticsservice.cassandra.QueryResultResponse;
import com.stackroute.analyticsservice.domain.AnalyticsOutput;
import com.stackroute.analyticsservice.domain.Input;
import com.stackroute.analyticsservice.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class AnalyticsServiceController {
    private ResponseEntity responseEntity;
    private final AnalyticsService analyticsService;
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceController.class);

    public AnalyticsServiceController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping("response")
    public ResponseEntity<String> processResponse(@RequestBody Input input){
        List<String> list = new ArrayList<>();
        try{
            if(analyticsService.existsByQuery(input.getQuery()))
            {
                analyticsService.updateQuery(input);
                list.add("Updated Successfully");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(list);
                responseEntity = new ResponseEntity<String>(json, HttpStatus.OK);
            }
            else
            {
                analyticsService.saveQuery(input);
                list.add("Added Successfully");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(list);
                responseEntity = new ResponseEntity<String>(json,HttpStatus.OK);
            }
        } catch (Exception e)
        {
            logger.warn(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("display")
    public ResponseEntity<String> showResults() {
        try {
            List<QueryResultResponse> list = analyticsService.getResults();
            QueryResultResponse[] list1 = new QueryResultResponse[list.size()];
            int i=0;
            for(QueryResultResponse a:list)
            {
                list1[i] = a;
                i++;
            }
            responseEntity = new ResponseEntity<QueryResultResponse[]>(list1,HttpStatus.OK);
        } catch (Exception e)
        {
            logger.warn(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("display/movie")
    public ResponseEntity<String> showMovieResults() {
        try {
            List<QueryResultResponse> list = analyticsService.getResultsByDomain("movie");
            QueryResultResponse[] list1 = new QueryResultResponse[list.size()];
            int i=0;
            for(QueryResultResponse a:list)
            {
                list1[i] = a;
                i++;
            }
            responseEntity = new ResponseEntity<QueryResultResponse[]>(list1,HttpStatus.OK);
        } catch (Exception e)
        {
            logger.warn(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("display/medical")
    public ResponseEntity<String> showMedicalResults() {
        try {
            List<QueryResultResponse> list = analyticsService.getResultsByDomain("medical");
            QueryResultResponse[] list1 = new QueryResultResponse[list.size()];
            int i=0;
            for(QueryResultResponse a:list)
            {
                list1[i] = a;
                i++;
            }
            responseEntity = new ResponseEntity<QueryResultResponse[]>(list1,HttpStatus.OK);
        } catch (Exception e)
        {
            logger.warn(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("analytics")
    public ResponseEntity<String> getGraphData() {
        try {
            responseEntity = new ResponseEntity<AnalyticsOutput>(analyticsService.getAnalyticsData(),HttpStatus.OK);
        } catch (Exception e)
        {
            logger.warn(e.getMessage());
            responseEntity = new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
