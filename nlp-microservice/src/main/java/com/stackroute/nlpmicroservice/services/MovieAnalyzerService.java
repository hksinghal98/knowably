package com.stackroute.nlpmicroservice.services;

import com.stackroute.nlpmicroservice.domain.Movie;
import com.stackroute.nlpmicroservice.model.MovieDictionary;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieAnalyzerService {
    private MovieDictionary movieDictionary;
    private Movie movie;

    @Autowired
    public MovieAnalyzerService(MovieDictionary movieDictionary, Movie movie) {
        this.movieDictionary = movieDictionary;
        this.movie=movie;
    }

    void extractInfo(String concept,CoreMap sentence,
                     LinkedHashMap<String, java.util.List<String>> mapOfEachWordProps,
                     LinkedHashMap<String, String> entInSentence) {
        System.out.println("\nsentence="+sentence);
        System.out.println("\nm1="+mapOfEachWordProps);
        System.out.println("\nm2="+entInSentence);

        movie.setMovieName(concept);

        HashMap<Integer,String> matched_points=new HashMap<>();
        ArrayList<Integer> indices=new ArrayList<>();
        movieDictionary.movieInitializer();
        int index;
        for(int i=0;i<movieDictionary.getStarring().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getStarring().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"actor");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getProducer().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getProducer().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"producer");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getDirector().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getDirector().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"director");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getReleasedyear().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getReleasedyear().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"year");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getProductionhouse().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getProductionhouse().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"productionHouse");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getCollections().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getCollections().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"collection");
                indices.add(index);
            }
        }
        for(int i=0;i<movieDictionary.getCountry().size();i++)
        {
            index=sentence.toString().toLowerCase().indexOf(movieDictionary.getCountry().get(i).toLowerCase());
            if(index!=-1)
            {
                matched_points.put(index,"country");
                indices.add(index);
            }
        }
        matched_points.put(sentence.toString().length(),"EOS");
        indices.add(sentence.toString().length());
        LinkedHashSet<Integer> hashSet1 = new LinkedHashSet<>(indices);
        indices = new ArrayList<>(hashSet1);
        Collections.sort(indices);

        System.out.println("\n**************************************\noutside first for loop");
        System.out.println(matched_points.toString());
        System.out.println(indices.toString());


        for(int i=0;i<indices.size()-1;i++)
        {
            if(matched_points.get(indices.get(i)).equalsIgnoreCase("actor")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("person") && sub_str.contains(s)) {
                        movie.getCasts().add(s);
                    }
                }
            }

            if(matched_points.get(indices.get(i)).equalsIgnoreCase("producer")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("person") && sub_str.contains(s)) {
                        movie.getProducers().add(s);
                    }
                }
            }

            if(matched_points.get(indices.get(i)).equalsIgnoreCase("director")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("person") && sub_str.contains(s)) {
                        movie.getDirectors().add(s);
                    }
                }
            }
            if(matched_points.get(indices.get(i)).equalsIgnoreCase("year")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("DATE") && sub_str.contains(s)) {
                        movie.getRelease_date().add(s);
                    }
                }

            }
            if(matched_points.get(indices.get(i)).equalsIgnoreCase("productionHouse")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("ORGANIZATION") && sub_str.contains(s)) {
                        movie.getProduction_house().add(s);
                    }
                }
            }
            if(matched_points.get(indices.get(i)).equalsIgnoreCase("collection")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("money") && sub_str.contains(s)) {
                        movie.getCollection().add(s); }
                }
            }
            if(matched_points.get(indices.get(i)).equalsIgnoreCase("country")) {
                String sub_str = sentence.toString().substring(indices.get(i), indices.get(i + 1));
                List<String> l=new ArrayList<>(entInSentence.keySet());
                for (String s : l) {
                    if (entInSentence.get(s).equalsIgnoreCase("country") && sub_str.contains(s)) {
                        movie.getCountry().add(s);
                    }
                }
            }
        }
        System.out.println("\nmovie after each sentence===== "+movie.toString());
        matched_points.clear();
        indices.clear();
        index=0;
    }
}
