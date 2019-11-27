package com.stackroute.nlpmicroservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medical {
    private String Disease;
    private List<String> Symptoms=new ArrayList<>();;
    private List<String> Medicine=new ArrayList<>();;
    private List<String> Causes=new ArrayList<>();;
    private List<String> DeathCount=new ArrayList<>();;
    private List<String> Treatment=new ArrayList<>();;

}
