package com.stackroute.datapopulator.domain;
import java.util.Map;

public class Node {
    private String uuid;
    private String type;
    private Map<String, String> properties;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Node() {
    }


}
