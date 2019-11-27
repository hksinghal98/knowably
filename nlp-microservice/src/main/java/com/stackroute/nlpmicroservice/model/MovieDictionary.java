package com.stackroute.nlpmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDictionary {
    ArrayList<String> starring = new ArrayList<>();
    ArrayList<String> director = new ArrayList<>();
    ArrayList<String> producer = new ArrayList<>();
    ArrayList<String> releasedyear = new ArrayList<>();
    ArrayList<String> productionhouse = new ArrayList<>();
    ArrayList<String> collections=new ArrayList<>();
    ArrayList<String> country=new ArrayList<>();


    public void movieInitializer() {

        //star cast dictionary
        starring.add("starring");
        starring.add("casting");
        starring.add("casted");
        starring.add("cast");
        starring.add("actor");
        starring.add("actress");
        starring.add("star");
        starring.add("performer");
        starring.add("character");
        starring.add("comedian");
        starring.add("villain");
        starring.add("supporting actor");
        starring.add("supporting actress");
        starring.add("hero");
        starring.add("heroine");
        starring.add("superstar");
        starring.add("done");
        starring.add("fighter");
        starring.add("stars");
        starring.add("acting");
        starring.add("acted");
        starring.add("actors");
        starring.add("heroes");
        starring.add("actresses");

        // director dictionary

        director.add("Director");
        director.add("DIRECTED");
        director.add("administrator");
        director.add("assistant director");
        director.add("managing director");
        director.add("music director");
        director.add("directing");
        director.add("direct");
        director.add("direction");

        //producer dictionary

        producer.add("Producer");
        producer.add("PRODUCED");
        producer.add("filmmaker");
        producer.add("manufacturer");
        producer.add("produce");
        producer.add("maker");
        producer.add("promoter");
        producer.add("creator");
        producer.add("Producers");
        producer.add("co-produce");
        producer.add("co-produced");
        producer.add("generate");
        producer.add("producing");

        //production house dictionary
        productionhouse.add("company");
        productionhouse.add("production house");
        productionhouse.add("house");
        productionhouse.add("production company");
        productionhouse.add("production");
        productionhouse.add("made");
        productionhouse.add("making");
        productionhouse.add("distribute");


        // released year dictionary
        releasedyear.add("release year");
        releasedyear.add("RELEASES");
        releasedyear.add("year");
        releasedyear.add("date");
        releasedyear.add("released");
        releasedyear.add("release");
        releasedyear.add("releasing");
        releasedyear.add("Release in");
        releasedyear.add("release date");
        releasedyear.add("release date");
        releasedyear.add("releases");
        releasedyear.add("latest");
        releasedyear.add("first");
        releasedyear.add("last");
        releasedyear.add("première");


        collections.add("box office");
        collections.add("collection");
        collections.add("collect");
        collections.add("profit");
        collections.add("yield");
        collections.add("raise");
        collections.add("fund");
        collections.add("gross");
        collections.add("made");

        country.add("country");
        country.add("sight");
        country.add("countries");
        country.add("place");
        country.add("photography");
        country.add("release");
    }
}
