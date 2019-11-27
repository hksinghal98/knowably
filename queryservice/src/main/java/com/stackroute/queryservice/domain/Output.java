package com.stackroute.queryservice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Output {
    private String domain;
    private String query;
    private String[] queryResult;
    private List<Map> constraints;
    public Output() {
        constraints = new ArrayList<>();
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String[] getQueryResult() {
        return queryResult;
    }
    public void setQueryResult(String[] queryResult) {
        this.queryResult = queryResult;
    }
    public void setConstraint(List<Map> constraint)
    {
        this.constraints = constraint;
    }
    public List<Map> getConstraints() {
        return constraints;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
}
