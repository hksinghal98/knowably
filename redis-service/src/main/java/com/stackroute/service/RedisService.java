package com.stackroute.service;

import com.google.gson.Gson;
import com.stackroute.domain.Data;
import com.stackroute.domain.DataModel;
import com.stackroute.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class RedisService {
    @Autowired
    private DataRepository dataRepository;
    public void populate(String param) {
        Gson gson = new Gson();
        Data datamodel = gson.fromJson(param, Data.class);
        for (DataModel dm:datamodel.getInfo()
             ) {
            dataRepository.save(dm);
        }
    }
    public Map<String,DataModel> getAll(){
       return dataRepository.findAll();
    }
}
