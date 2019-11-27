package com.stackroute.nlpmicroservice.services;

import com.stackroute.nlpmicroservice.domain.SitesContent;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class NLPResultsService {

    private MovieAnalyzerService movieAnalyzerService;
    private MedicalAnalyzerService medicalAnalyzerService;
    private SitesContent sitesContent;

    public NLPResultsService(MovieAnalyzerService movieAnalyzerService, SitesContent sitesContent,
                             MedicalAnalyzerService medicalAnalyzerService) {
        this.movieAnalyzerService = movieAnalyzerService;
        this.sitesContent=sitesContent;
        this.medicalAnalyzerService=medicalAnalyzerService;
    }

    @Autowired
    StanfordCoreNLP stanfordCoreNLP;
    public void StepwiseNlp(String OnePara)
    {
        CoreDocument document=new CoreDocument(OnePara);
        Annotation annotations = new Annotation(OnePara);
        //List<CoreLabel> coreLabelList=document.tokens();
        //stanfordCoreNLP.annotate(document);
        stanfordCoreNLP.annotate(annotations);

        List<CoreMap> sentences = annotations.get(CoreAnnotations.SentencesAnnotation.class);
        LinkedHashMap<String, List<String>> mapOfEachWordProps = new LinkedHashMap<>();
        LinkedHashMap<String,String> entInSentence=new LinkedHashMap<>();


        for(CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                mapOfEachWordProps.putIfAbsent(word,new ArrayList<>(Arrays.asList(pos,ne)));
            }
            //System.out.println(mapOfEachWordProps);
            for (CoreMap entityMention : sentence.get(CoreAnnotations.MentionsAnnotation.class)) {
               String ner=entityMention.get(CoreAnnotations.NamedEntityTagAnnotation.class);
               String w=entityMention.get(CoreAnnotations.TextAnnotation.class);
               entInSentence.putIfAbsent(w,ner);
            }
            //System.out.println(entInSentence);
            String domain=sitesContent.getDomain();
            String concept=sitesContent.getConcept();

            if(domain.equalsIgnoreCase("movie"))
                movieAnalyzerService.extractInfo(concept,sentence,mapOfEachWordProps,entInSentence);
            else if(domain.equalsIgnoreCase("medical"))
                medicalAnalyzerService.extractInfo(concept,sentence,mapOfEachWordProps,entInSentence);
            else
                System.out.println("no analysis done in nlp result service");

            mapOfEachWordProps.clear();
            entInSentence.clear();
        }
    }

}
