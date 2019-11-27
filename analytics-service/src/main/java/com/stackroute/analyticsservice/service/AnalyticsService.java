package com.stackroute.analyticsservice.service;

import com.stackroute.analyticsservice.cassandra.QueryResultResponse;
import com.stackroute.analyticsservice.domain.AnalyticsOutput;
import com.stackroute.analyticsservice.domain.Input;

import java.util.List;

public interface AnalyticsService {
    public boolean existsByQuery(String query);
    public void saveQuery(Input input);
    public void updateQuery(Input input);
    public List<QueryResultResponse> getResults();
    public List<QueryResultResponse> getResultsByDomain(String domain);
    public AnalyticsOutput getAnalyticsData();
}
