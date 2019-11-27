package com.stackroute.nlpmicroservice.core;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

@NoArgsConstructor
@Service
public class Pipeline {
    private static Properties properties;
    private static String propertiesName="tokenize, ssplit, pos, lemma, ner"; //, parse, coref
    private  static StanfordCoreNLP stanfordCoreNLP;
    //props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
    //props.setProperty("coref.algorithm", "neural");


    @Bean(name="stanfordCoreNLP")
    public static StanfordCoreNLP getInstance()
    {
        properties =new Properties();
        properties.setProperty("annotators",propertiesName);
        //properties.setProperty("coref.algorithm", "neural");

        if(stanfordCoreNLP==null)
        { stanfordCoreNLP=new StanfordCoreNLP(properties); }
        return stanfordCoreNLP;
    }

}
