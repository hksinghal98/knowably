package com.stackroute.repository;

import com.stackroute.domain.DataModel;

import java.util.Map;

public interface DataRepository {

    void save(DataModel dataModel);
    Map<String, DataModel> findAll();
//    DataModel findByName(String name);
//    void update(DataModel dataModel);
//    void delete(String name);
}
