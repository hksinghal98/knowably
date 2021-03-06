package com.stackroute.queryservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.queryservice.domain.Output;
import com.stackroute.queryservice.domain.QueryInput;
import com.stackroute.queryservice.domain.QueryOutput;
import com.stackroute.queryservice.service.MedicineQueryService;
import com.stackroute.queryservice.service.MovieQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(value = "QuerySearch Rest API")
@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class QueryServiceController {
    private ResponseEntity responseEntity;
    private String topic="QueryResult";

    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public QueryServiceController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @ApiOperation(value = "Perform NLP on User Query")
    @PostMapping("query")
    public ResponseEntity<?> processQuery(@RequestBody QueryInput queryInput) throws JsonProcessingException {
        if (queryInput.getDomain().equalsIgnoreCase("movie")) {
            try {
                MovieQueryService movieQueryNlp = new MovieQueryService();
                List<String> lemmatizedList = movieQueryNlp.getLemmatizedList(queryInput.getSearchTerm());
                String stringWithoutStopWords = "";
                for (int i = 0; i < lemmatizedList.size(); i++) {
                    stringWithoutStopWords = stringWithoutStopWords + lemmatizedList.get(i) + " ";
                }
                Output output = movieQueryNlp.RedisMatcher(stringWithoutStopWords.toLowerCase());
                output.setQuery(queryInput.getSearchTerm());
                QueryOutput queryOutput = new QueryOutput();
                queryOutput.setDomain(queryInput.getDomain());
                queryOutput.setQuery(stringWithoutStopWords);
                queryOutput.setConstraints(output.getConstraints());
                String[] queryResult = output.getQueryResult();
                List<String> finalQueryResult = new ArrayList<>();
                for (String a : queryResult) {
                    finalQueryResult.add(a);
                }
                queryOutput.setQueryresult(finalQueryResult);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(queryOutput);
                kafkaTemplate.send(topic, json);
                responseEntity = new ResponseEntity<String>(json, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
            }

        } else if (queryInput.getDomain().equalsIgnoreCase("medical")) {
            try {
                MedicineQueryService medicineQueryNlp = new MedicineQueryService();
                List<String> lemmatizedList = medicineQueryNlp.getLemmatizedList(queryInput.getSearchTerm());
                String stringWithoutStopWords = "";
                for (int i = 0; i < lemmatizedList.size(); i++) {
                    stringWithoutStopWords = stringWithoutStopWords + lemmatizedList.get(i) + " ";
                }
                Output output = medicineQueryNlp.RedisMatcher(stringWithoutStopWords.toLowerCase());
                output.setQuery(queryInput.getSearchTerm());
                QueryOutput queryOutput = new QueryOutput();
                queryOutput.setDomain(queryInput.getDomain());
                queryOutput.setQuery(stringWithoutStopWords);
                queryOutput.setConstraints(output.getConstraints());
                String[] queryResult = output.getQueryResult();
                List<String> finalQueryResult = new ArrayList<>();
                for (String a : queryResult) {
                    finalQueryResult.add(a);
                }
                queryOutput.setQueryresult(finalQueryResult);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(queryOutput);
                kafkaTemplate.send(topic, json);
                responseEntity = new ResponseEntity<String>(json, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
                e.printStackTrace();
            }
        }
        return responseEntity;
    }
}
