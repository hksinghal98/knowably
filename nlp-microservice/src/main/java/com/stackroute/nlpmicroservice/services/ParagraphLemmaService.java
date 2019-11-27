package com.stackroute.nlpmicroservice.services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParagraphLemmaService {

    @Autowired
    StanfordCoreNLP stanfordCoreNLP;

    public ArrayList<String> getLemmieWords(String p) {
        Annotation annotations = new Annotation(p);
        stanfordCoreNLP.annotate(annotations);

        ArrayList<String> lemmaWords = new ArrayList<>();
        ArrayList<String> originalWords = new ArrayList<>();
        List<CoreMap> sentenceList = annotations.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentenceList) {
            for (CoreLabel word : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                lemmaWords.add(word.lemma());
                originalWords.add(word.originalText());
            }
        }
        //System.out.println("lemma words for each this sentence = "+lemmaWords);
        //System.out.println("original words for this sentence ="+originalWords);
        return lemmaWords;
    }
    public String getParagraphWithLemmieWords(String p)
    {
        p = p.trim();
        p=p.replaceAll("\\[.*?\\]", "");
        p=p.replaceAll("\\(.*?\\)", "");
        ArrayList<String> LemmieWords = getLemmieWords(p);
        StringBuffer paragraphWithLemmatizedWords = new StringBuffer();
        for (int i = 0; i < LemmieWords.size(); i++) {
            paragraphWithLemmatizedWords.append(LemmieWords.get(i) + " ");
        }
        return paragraphWithLemmatizedWords.toString().trim();
    }
}
