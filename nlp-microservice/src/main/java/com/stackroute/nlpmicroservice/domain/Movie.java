package com.stackroute.nlpmicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private String movieName;
    private List<String> casts=new ArrayList<>();
    private List<String> producers= new ArrayList<>();
    private List<String> directors= new ArrayList<>();
    private List<String> collection= new ArrayList<>();
    private List<String> release_date= new ArrayList<>();
    private List<String> production_house= new ArrayList<>();
    private List<String> country= new ArrayList<>();
}
