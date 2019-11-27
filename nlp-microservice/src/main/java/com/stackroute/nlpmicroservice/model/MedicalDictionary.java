package com.stackroute.nlpmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalDictionary {
    List<String> Symptoms = new ArrayList<>();
    List<String> Medicine= new ArrayList<>();
    List<String> Causes= new ArrayList<>();
    List<String> DeathCount= new ArrayList<>();
    List<String> Treatment= new ArrayList<>();

    public void medicalInitializer()
    {
        Symptoms.add("symptom");
        Symptoms.add("shows");
        Symptoms.add("cause of");
        Symptoms.add("manifestation");
        Symptoms.add("expression");
        Symptoms.add("indication");
        Symptoms.add("sign");
        Symptoms.add("characteristic");
        Symptoms.add("include");

        Medicine.add("medicine");
        Medicine.add("medication");
        Medicine.add("drug");
        Medicine.add("antibiotic");
        Medicine.add("cure");
        Medicine.add("medication");
        Medicine.add("pharmaceutical");
        Medicine.add("prescription");
        Medicine.add("remedy");
        Medicine.add("capsule");
        Medicine.add("tablet");


        Causes.add("cause by");
        Causes.add("lead");
        Causes.add("condition");

        DeathCount.add("death");
        DeathCount.add("death count");
        DeathCount.add("die");
        DeathCount.add("kill");
        DeathCount.add("casualty");
        DeathCount.add("fatality");
        DeathCount.add("suffer");


        Treatment.add("therapy");
        Treatment.add("healing");
        Treatment.add("operation");
        Treatment.add("diagnose");
        Treatment.add("test");
        Treatment.add("analyze");
        //Treatment.add("treatment");




    }
}
